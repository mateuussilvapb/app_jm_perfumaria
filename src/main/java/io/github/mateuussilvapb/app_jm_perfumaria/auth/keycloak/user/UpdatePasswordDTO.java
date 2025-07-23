package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordDTO {
    public String currentPassword;
    private String newPassword;
}