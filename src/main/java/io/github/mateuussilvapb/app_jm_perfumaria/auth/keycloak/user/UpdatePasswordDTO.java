package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordDTO {
    private String newPassword;
}