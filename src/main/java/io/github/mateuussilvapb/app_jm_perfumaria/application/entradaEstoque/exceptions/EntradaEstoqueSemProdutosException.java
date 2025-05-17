package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions;

public class EntradaEstoqueSemProdutosException extends RuntimeException {
    public EntradaEstoqueSemProdutosException() {
        super("Entrada de estoque deve conter ao menos um produto");
    }
}
