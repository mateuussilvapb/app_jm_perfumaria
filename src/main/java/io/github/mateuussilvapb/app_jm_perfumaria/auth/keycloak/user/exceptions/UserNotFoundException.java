package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
