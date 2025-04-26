package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.exceptions;

public class CategoriaSameNameException extends RuntimeException {
    public CategoriaSameNameException(String name) {
        super("Categoria com o nome:  " + name + " já está cadastrada e ativa.");
    }
}
