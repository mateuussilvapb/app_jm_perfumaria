package io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions;

public class SelfToggleStatusException extends RuntimeException {
    public SelfToggleStatusException() {
        super("Você não pode habilitar/desabilitar o seu próprio usuário.");
    }
}
