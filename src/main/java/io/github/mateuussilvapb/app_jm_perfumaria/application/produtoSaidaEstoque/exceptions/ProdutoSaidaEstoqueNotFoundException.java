package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.exceptions;

public class ProdutoSaidaEstoqueNotFoundException extends RuntimeException {
    public ProdutoSaidaEstoqueNotFoundException(Long id) {
        super("A saida de produto no estoque com o id: '" + id + "' não foi localizada");
    }
}
