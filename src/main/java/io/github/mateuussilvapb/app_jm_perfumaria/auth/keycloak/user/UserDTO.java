package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<UserRoles> roles;

}