package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.IncorrectCurrentPasswordException;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.UserRoles;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakUserService {
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

    public Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder().serverUrl(authServerUrl).realm("master") // The realm for admin operations is always "master"
                .clientId("admin-cli").username(adminUsername).password(adminPassword).build();
    }

    public boolean createKeycloakUser(String username, String email, String firstName, String lastName, String password, List<String> roles) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Define user
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(true);

        // Create user
        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {
            String userId = getCreatedId(response);

            // Define password credential
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);

            // Set password credential
            usersResource.get(userId).resetPassword(passwordCred);

            removeDefaultRealmRolesFromUser(userId);

            // Assign realm roles to user if provided
            if (roles != null && !roles.isEmpty()) {
                assignRolesToUser(userId, roles);
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Simpler overload method without roles
     */
    public boolean createKeycloakUser(String username, String email, String firstName, String lastName, String password) {
        return createKeycloakUser(username, email, firstName, lastName, password, null);
    }

    public boolean assignRolesToUser(String userId, List<String> roleNames) {
        try {
            Keycloak keycloak = getKeycloakInstance();
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            UserResource userResource = usersResource.get(userId);

            // Atribuir roles do tipo realm
            List<RoleRepresentation> realmRoles = realmResource.roles().list();
            List<RoleRepresentation> realmRolesToAdd = new ArrayList<>();
            for (String roleName : roleNames) {
                realmRoles.stream().filter(role -> role.getName().equals(roleName)).findFirst().ifPresent(realmRolesToAdd::add);
            }
            if (!realmRolesToAdd.isEmpty()) {
                userResource.roles().realmLevel().add(realmRolesToAdd);
            }

            // Atribuir roles do tipo client
            List<ClientRepresentation> clients = realmResource.clients().findByClientId(clientId);
            if (!clients.isEmpty()) {
                String clientUuid = clients.get(0).getId();
                List<RoleRepresentation> clientRoles = realmResource.clients().get(clientUuid).roles().list();

                List<RoleRepresentation> clientRolesToAdd = new ArrayList<>();
                for (String roleName : roleNames) {
                    clientRoles.stream().filter(role -> role.getName().equals(roleName)).findFirst().ifPresent(clientRolesToAdd::add);
                }

                if (!clientRolesToAdd.isEmpty()) {
                    userResource.roles().clientLevel(clientUuid).add(clientRolesToAdd);
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Assign specific application roles to a user
     */
    public boolean assignApplicationRolesToUser(String userId, List<UserRoles> roles) {
        List<String> roleNames = roles.stream().map(Enum::name).toList();

        return assignRolesToUser(userId, roleNames);
    }

    /**
     * Create user with application-specific roles
     */
    public boolean createKeycloakUserWithAppRoles(String username, String email, String firstName, String lastName, String password, List<UserRoles> roles) {
        List<String> roleNames = null;
        if (roles != null) {
            roleNames = roles.stream().map(Enum::name).toList();
        }

        return createKeycloakUser(username, email, firstName, lastName, password, roleNames);
    }

    public List<UserRepresentation> getKeycloakUsers() {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        return usersResource.list();
    }

    public UserRepresentation getKeycloakUserByUsername(String username) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> users = usersResource.search(username, true);
        return users.isEmpty() ? null : users.getFirst();
    }

    public boolean updateKeycloakUser(String userId, String firstName, String lastName, String email, List<UserRoles> userRoles) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = usersResource.get(userId).toRepresentation();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRealmRoles(userRoles.stream().map(String::valueOf).toList());

        usersResource.get(userId).update(user);
        return true;
    }

    public boolean deleteKeycloakUser(String userId) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        usersResource.get(userId).remove();
        return true;
    }

    // Helper method to extract the user ID from the response
    private String getCreatedId(Response response) {
        String location = response.getHeaderString("Location");
        if (location != null) {
            return location.substring(location.lastIndexOf("/") + 1);
        }
        return null;
    }

    public List<UserRoles> getUserAppRoles(String userId) {
        try {
            Keycloak keycloak = getKeycloakInstance();
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);

            // Obtem os roles de nível de realm atribuídos ao usuário
            List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listEffective();

            // Converte para UserRoles, filtrando apenas os que estão definidos no enum
            List<UserRoles> userRoles = new ArrayList<>();
            for (RoleRepresentation role : realmRoles) {
                try {
                    userRoles.add(UserRoles.valueOf(role.getName()));
                } catch (IllegalArgumentException e) {
                    // Ignora roles que não pertencem ao enum UserRoles
                }
            }

            return userRoles;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Retorna lista vazia em caso de erro
        }
    }

    public boolean userHasRole(String userId, UserRoles role) {
        try {
            Keycloak keycloak = getKeycloakInstance();
            RealmResource realmResource = keycloak.realm(realm);
            UserResource userResource = realmResource.users().get(userId);

            // Lista todos os roles efetivos do usuário
            List<RoleRepresentation> realmRoles = userResource.roles().realmLevel().listEffective();

            // Verifica se o nome do role está entre os retornados
            return realmRoles.stream().anyMatch(r -> r.getName().equals(role.name()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void removeDefaultRealmRolesFromUser(String userId) {
        Keycloak keycloak = getKeycloakInstance();
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);

        List<String> defaultRoleNames = List.of("default-roles-jmperfumaria");

        List<RoleRepresentation> rolesToRemove = defaultRoleNames.stream().map(roleName -> realmResource.roles().get(roleName).toRepresentation()).collect(Collectors.toList());

        userResource.roles().realmLevel().remove(rolesToRemove);

    }

    public boolean updateLoggedUserPassword(UpdatePasswordDTO data) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();

            Keycloak keycloak = getKeycloakInstance();
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Buscar o usuário pelo ID para pegar o username
            UserRepresentation user = usersResource.get(userId).toRepresentation();
            String username = user.getUsername();

            // Valida se a senha atual informada está correta.
            try {
                Keycloak keycloakAuth = KeycloakBuilder.builder()
                        .serverUrl(authServerUrl)
                        .realm(realm)
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .username(username)
                        .password(data.getCurrentPassword())
                        .build();

                // Apenas chamar token() já força a validação
                keycloakAuth.tokenManager().getAccessToken();
            } catch (Exception ex) {
                throw new IncorrectCurrentPasswordException();
            }
            UserResource userResource = realmResource.users().get(userId);

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setTemporary(false);
            credential.setValue(data.getNewPassword());

            userResource.resetPassword(credential);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}