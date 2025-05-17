package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class ProdutoSameNameException extends RuntimeException {
    public ProdutoSameNameException(String nome) {
        super("Produto com o nome '" + nome + "' já cadastrado.");
    }
}
