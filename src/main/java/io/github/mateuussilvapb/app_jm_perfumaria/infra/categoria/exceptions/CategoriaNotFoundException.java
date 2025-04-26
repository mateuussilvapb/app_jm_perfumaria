package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.exceptions;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("Categoria com o id '" + id + "' não encontrada.");
    }
}
