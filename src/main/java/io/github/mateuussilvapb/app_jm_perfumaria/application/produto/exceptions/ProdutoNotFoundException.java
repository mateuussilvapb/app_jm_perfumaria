package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class ProdutoNotFoundException extends RuntimeException {
    public ProdutoNotFoundException(Long id) {
        super("Produto com o id '" + id + "' não encontrado.");
    }
}
