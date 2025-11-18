package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class ProdutoSemEstoqueException extends RuntimeException{
    public ProdutoSemEstoqueException(String nome) {
        super("O produto '" + nome +"' não possui estoque.");
    }
}
