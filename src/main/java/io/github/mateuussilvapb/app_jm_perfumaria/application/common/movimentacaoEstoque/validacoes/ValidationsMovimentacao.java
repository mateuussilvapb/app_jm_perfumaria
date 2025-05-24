package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.validacoes;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueSemProdutosException;

import java.util.List;

public class ValidationsMovimentacao {

    public static void validateIfProdutosExists(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new EntradaEstoqueSemProdutosException();
        }
    }
}
