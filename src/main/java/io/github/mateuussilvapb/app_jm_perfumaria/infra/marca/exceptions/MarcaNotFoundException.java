package io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.exceptions;

public class MarcaNotFoundException extends RuntimeException {
    public MarcaNotFoundException(Long id) {
        super("Marca com o id '" + id + "' não encontrada.");
    }
}
