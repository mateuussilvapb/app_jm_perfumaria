package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.exceptions;

public class ProdutoEntradaEstoqueNotFoundException extends RuntimeException {
    public ProdutoEntradaEstoqueNotFoundException(Long id) {
        super("A entrada de produto no estoque com o id: '" + id + "' não foi localizada");
    }
}
