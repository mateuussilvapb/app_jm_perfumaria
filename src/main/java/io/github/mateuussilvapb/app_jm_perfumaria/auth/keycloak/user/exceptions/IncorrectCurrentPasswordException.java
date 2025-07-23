package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class IncorrectCurrentPasswordException extends RuntimeException {
    public IncorrectCurrentPasswordException() {
        super("A senha atual informada é inválida.\n Informe uma senha válida ou utilize outro " +
                "método de alteração de senha.");
    }
}
