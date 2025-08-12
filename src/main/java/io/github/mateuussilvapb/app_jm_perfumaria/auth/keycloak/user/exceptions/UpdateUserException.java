package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class UpdateUserException extends RuntimeException {
    public UpdateUserException() {
        super("Houve um erro ao atualizar o usuário. Tente novamente depois ou contate o " +
                "administrador do sistema.");
    }
}
