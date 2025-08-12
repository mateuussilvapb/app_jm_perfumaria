package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.CreateUserException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.UpdateUserException;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return new ResponseEntity<>(keycloakUserService.getKeycloakUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable String userId) {
        return new ResponseEntity<>(keycloakUserService.getKeycloakUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/searchByParam")
    public ResponseEntity<List<UserResponseDTO>> getUserBySearchParam(@RequestParam(name = "searchParam", required = false) String searchParam) {
        List<UserResponseDTO> user = keycloakUserService.getKeycloakUserBySearchParam(searchParam);
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
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        boolean result = keycloakUserService.createKeycloakUserWithAppRoles(userDTO.getUsername(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword(), userDTO.getRoles());

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            throw new CreateUserException();
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
    public ResponseEntity<Void> updateUser(@PathVariable String userId,
                                           @RequestBody UserResponseDTO userDTO) {
        boolean result = keycloakUserService.updateKeycloakUser(userId, userDTO);

        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            throw new UpdateUserException();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (keycloakUserService.deleteKeycloakUser(userId)) {
            return ResponseEntity.noContent().build(); // 204 - operação concluída sem corpo
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody UpdatePasswordDTO data) {
        keycloakUserService.updateLoggedUserPassword(data);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/toggleStatus")
    public ResponseEntity<Void> toggleStatusUserStatus(@PathVariable String id) {
        keycloakUserService.toggleUserStatus(id);
        return ResponseEntity.ok().build();
    }
}