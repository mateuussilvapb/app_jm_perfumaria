package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class ToggleUserStatusException extends RuntimeException {
    public ToggleUserStatusException() {
        super("Houve um erro ao alterar o status do usuário. Tente novamente ou entre em contato " +
                "com o administrador do sistema.");
    }
}
