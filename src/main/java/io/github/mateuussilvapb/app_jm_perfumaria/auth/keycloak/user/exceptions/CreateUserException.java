package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class CreateUserException extends RuntimeException {
    public CreateUserException() {
        super("Houve um erro ao criar o usuário. Tente novamente depois ou contate o " +
                "administrador do sistema.");
    }
}
