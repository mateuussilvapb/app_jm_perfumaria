package io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.exceptions;

public class MarcaSameNameException extends RuntimeException {
    public MarcaSameNameException(String name) {
        super("Marca com o nome:  " + name + " já está cadastrada e ativa.");
    }
}
