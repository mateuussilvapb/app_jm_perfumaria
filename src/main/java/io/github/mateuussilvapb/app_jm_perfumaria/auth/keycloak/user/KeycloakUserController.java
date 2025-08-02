package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.IncorrectCurrentPasswordException;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RolesAllowed({"admin"})
public class KeycloakUserController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @GetMapping
    public ResponseEntity<List<UserRepresentation>> getUsers() {
        List<UserRepresentation> users = keycloakUserService.getKeycloakUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserRepresentation> getUserByUsername(@PathVariable String username) {
        UserRepresentation user = keycloakUserService.getKeycloakUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<UserRoles>> getUserRoles(@PathVariable String userId) {
        List<UserRoles> roles = keycloakUserService.getUserAppRoles(userId);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{userId}/has-role/{role}")
    public ResponseEntity<Boolean> checkUserRole(@PathVariable String userId, @PathVariable UserRoles role) {
        boolean hasRole = keycloakUserService.userHasRole(userId, role);
        return new ResponseEntity<>(hasRole, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        boolean result = keycloakUserService.createKeycloakUserWithAppRoles(userDTO.getUsername(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword(), userDTO.getRoles());

        if (result) {
            return new ResponseEntity<>("Usuário criado com sucesso.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Falha ao criar usuário.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<String> assignRoles(@PathVariable String userId, @RequestBody List<UserRoles> roles) {
        boolean result = keycloakUserService.assignApplicationRolesToUser(userId, roles);

        if (result) {
            return new ResponseEntity<>("Permissões assinadas com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao assinar permissões.", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        boolean result = keycloakUserService.updateKeycloakUser(userId, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getRoles());

        if (result) {
            return new ResponseEntity<>("Usuário alterado com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao alterar usuário.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        boolean result = keycloakUserService.deleteKeycloakUser(userId);

        if (result) {
            return new ResponseEntity<>("Usuário removido com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao remover usuário.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody UpdatePasswordDTO data) {
        boolean alterada = keycloakUserService.updateLoggedUserPassword(data);
        if (alterada) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            throw new IncorrectCurrentPasswordException();
        }
    }
}