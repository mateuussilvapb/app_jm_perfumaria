package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.IncorrectCurrentPasswordException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.SelfToggleStatusException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.UserNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.UserRoles;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KeycloakUserService {

    private static final Logger log = LoggerFactory.getLogger(KeycloakUserService.class);

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    // ===== Helpers para instâncias Keycloak =====
    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder().serverUrl(authServerUrl).realm("master").clientId("admin-cli").username(adminUsername).password(adminPassword).build();
    }

    private RealmResource getRealmResource() {
        return getKeycloakInstance().realm(realm);
    }

    private UsersResource getUsersResource() {
        return getRealmResource().users();
    }

    private UserResource getUserResource(String userId) {
        return getUsersResource().get(userId);
    }

    // ===== Criação de usuário =====
    public boolean createKeycloakUser(String username, String email, String firstName, String lastName, String password, List<String> roles) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(true);

        Response response = getUsersResource().create(user);
        if (response.getStatus() != 201) return false;

        String userId = getCreatedId(response);
        setPassword(userId, password);
        removeDefaultRealmRolesFromUser(userId);

        if (roles != null && !roles.isEmpty()) {
            assignRolesToUser(userId, roles);
        }
        return true;
    }

    public boolean createKeycloakUser(String username, String email, String firstName, String lastName, String password) {
        return createKeycloakUser(username, email, firstName, lastName, password, null);
    }

    public boolean createKeycloakUserWithAppRoles(String username, String email, String firstName, String lastName, String password, List<UserRoles> roles) {
        return createKeycloakUser(username, email, firstName, lastName, password, roles != null ? roles.stream().map(Enum::name).toList() : null);
    }

    // ===== Gestão de roles =====
    public boolean assignRolesToUser(String userId, List<String> roleNames) {
        try {
            RealmResource realmResource = getRealmResource();
            UserResource userResource = getUserResource(userId);

            List<RoleRepresentation> realmRolesToAdd = findRoles(realmResource.roles().list(), roleNames);
            if (!realmRolesToAdd.isEmpty()) {
                userResource.roles().realmLevel().add(realmRolesToAdd);
            }

            List<ClientRepresentation> clients = realmResource.clients().findByClientId(clientId);
            if (!clients.isEmpty()) {
                String clientUuid = clients.getFirst().getId();
                List<RoleRepresentation> clientRolesToAdd = findRoles(realmResource.clients().get(clientUuid).roles().list(), roleNames);
                if (!clientRolesToAdd.isEmpty()) {
                    userResource.roles().clientLevel(clientUuid).add(clientRolesToAdd);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Erro ao atribuir roles para usuário {}", userId, e);
            return false;
        }
    }

    public boolean assignApplicationRolesToUser(String userId, List<UserRoles> roles) {
        return assignRolesToUser(userId, roles.stream().map(Enum::name).toList());
    }

    private List<RoleRepresentation> findRoles(List<RoleRepresentation> availableRoles, List<String> roleNames) {
        return roleNames.stream().map(name -> availableRoles.stream().filter(r -> r.getName().equals(name)).findFirst().orElse(null)).filter(Objects::nonNull).toList();
    }

    public List<UserRoles> getUserAppRoles(String userId) {
        try {
            RealmResource realmResource = getRealmResource();
            ClientRepresentation client = realmResource.clients().findByClientId(clientId).getFirst();
            String clientUuid = client.getId();

            List<RoleRepresentation> clientRoles = getUserResource(userId).roles().clientLevel(clientUuid).listEffective();
            return clientRoles.stream().map(RoleRepresentation::getName).map(name -> {
                try {
                    return UserRoles.valueOf(name);
                } catch (IllegalArgumentException e) {
                    log.warn("Ignorando role não mapeada: {}", name);
                    return null;
                }
            }).filter(Objects::nonNull).toList();
        } catch (Exception e) {
            log.error("Erro ao buscar roles do usuário {}", userId, e);
            return List.of();
        }
    }

    public boolean userHasRole(String userId, UserRoles role) {
        try {
            return getUserResource(userId).roles().realmLevel().listEffective().stream().anyMatch(r -> r.getName().equals(role.name()));
        } catch (Exception e) {
            log.error("Erro ao verificar role {} no usuário {}", role, userId, e);
            return false;
        }
    }

    // ===== Busca de usuários =====
    public List<UserResponseDTO> getKeycloakUsers() {
        return getUsersResource().list().stream().map(this::toUserResponseDTO).toList();
    }

    public UserResponseDTO getKeycloakUserById(String userId) {
        return getKeycloakUsers().stream().filter(user -> user.getId().equals(userId)).findFirst().orElseThrow(UserNotFoundException::new);
    }

    public List<UserResponseDTO> getKeycloakUserBySearchParam(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) return getKeycloakUsers();

        searchTerm = searchTerm.trim().toLowerCase().replaceAll(" ", "");

        Map<String, UserRepresentation> usersById = Stream.concat(getKeycloakUserByUsername(searchTerm).stream(), getKeycloakUserByEmail(searchTerm).stream()).collect(Collectors.toMap(UserRepresentation::getId, Function.identity(), (u1, u2) -> u1, LinkedHashMap::new));

        return usersById.values().stream().map(this::toUserResponseDTO).toList();
    }

    public List<UserRepresentation> getKeycloakUserByUsername(String username) {
        return searchUsers(users -> users.search(username, false));
    }

    public List<UserRepresentation> getKeycloakUserByEmail(String email) {
        return searchUsers(users -> users.searchByEmail(email, false));
    }

    private List<UserRepresentation> searchUsers(Function<UsersResource, List<UserRepresentation>> searchFn) {
        List<UserRepresentation> users = searchFn.apply(getUsersResource());
        return users.isEmpty() ? List.of() : users;
    }

    private UserResponseDTO toUserResponseDTO(UserRepresentation u) {
        return new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail(), u.getFirstName(), u.getLastName(), u.isEnabled(), getUserAppRoles(u.getId()));
    }

    // ===== Atualização e exclusão =====
    public boolean updateKeycloakUser(String userId, UserResponseDTO dto) {
        //Recupera o usuário pelo id;
        UserRepresentation user = getUserResource(userId).toRepresentation();

        //Seta as informações básicas
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        //Remove todas as roles do usuário
        removeAllRolesFromUser(userId);

        //Assina as novas roles para o usuário
        assignRolesToUser(userId, dto.getRoles().stream().map(Enum::name).toList());

        //Atualiza o usuário com as novas informações
        getUserResource(userId).update(user);
        return true;
    }

    public void removeAllRolesFromUser(String userId) {
        UserResource userResource = getUserResource(userId);

        // 1) Remove roles de realm atribuídas diretamente ao usuário
        List<RoleRepresentation> realmRolesAssigned = userResource.roles().realmLevel().listAll(); // roles atribuídas
        if (realmRolesAssigned != null && !realmRolesAssigned.isEmpty()) {
            userResource.roles().realmLevel().remove(realmRolesAssigned);
            log.info("Removidas {} realm-roles do usuário {}", realmRolesAssigned.size(), userId);
        }

        // 2) Remove roles de client atribuídas diretamente ao usuário (todos os clients do realm)
        List<ClientRepresentation> appClients = getRealmResource().clients().findByClientId(clientId);
        if (!appClients.isEmpty()) {
            String appClientUuid = appClients.getFirst().getId();
            List<RoleRepresentation> appClientRoles = userResource.roles().clientLevel(appClientUuid).listAll();
            if (!appClientRoles.isEmpty()) {
                userResource.roles().clientLevel(appClientUuid).remove(appClientRoles);
            }
        }
    }


    public boolean deleteKeycloakUser(String userId) {
        getUserResource(userId).remove();
        return true;
    }

    // ===== Senha =====
    private void setPassword(String userId, String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        getUserResource(userId).resetPassword(passwordCred);
    }

    public boolean updateLoggedUserPassword(UpdatePasswordDTO data) {
        UserRepresentation user = getLoggedUserRepresentation();
        String username = user.getUsername();

        // Valida senha atual
        try {
            Keycloak keycloakAuth = KeycloakBuilder.builder().serverUrl(authServerUrl).realm(realm).clientId(clientId).clientSecret(clientSecret).username(username).password(data.getCurrentPassword()).build();
            keycloakAuth.tokenManager().getAccessToken();
        } catch (Exception ex) {
            throw new IncorrectCurrentPasswordException();
        }

        setPassword(user.getId(), data.getNewPassword());
        return true;
    }

    // ===== Utilitários =====
    private String getCreatedId(Response response) {
        String location = response.getHeaderString("Location");
        return (location != null) ? location.substring(location.lastIndexOf("/") + 1) : null;
    }

    private void removeDefaultRealmRolesFromUser(String userId) {
        List<String> defaultRoleNames = List.of("default-roles-jmperfumaria");
        List<RoleRepresentation> rolesToRemove = defaultRoleNames.stream().map(roleName -> getRealmResource().roles().get(roleName).toRepresentation()).toList();
        getUserResource(userId).roles().realmLevel().remove(rolesToRemove);
    }

    private UserRepresentation getLoggedUserRepresentation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserResource(authentication.getName()).toRepresentation();
    }

    public void toggleUserStatus(String userId) {
        UserRepresentation loggedUser = getLoggedUserRepresentation();
        if (loggedUser.getId().equals(userId)) throw new SelfToggleStatusException();

        UserResource userResource = getUserResource(userId);
        UserRepresentation targetUser = userResource.toRepresentation();
        targetUser.setEnabled(!targetUser.isEnabled());
        userResource.update(targetUser);
    }
}
