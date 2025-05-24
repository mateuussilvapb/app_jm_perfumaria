package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.validacoes;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.PrecoUnitarioInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.ValorDescontoInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueSemProdutosException;

import java.math.BigDecimal;
import java.util.List;

public class ValidationsMovimentacao {

    public static void validateIfProdutosExists(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new EntradaEstoqueSemProdutosException();
        }
    }

    public static void validateIfPrecoLessThenOne(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        produtos.forEach(p -> {
            if (p.precoUnitario().compareTo(new BigDecimal(1)) == -1) {
                throw new PrecoUnitarioInvalidoException();
            }
        });
    }

    public static void validateIfDescontoLessThenOne(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        produtos.forEach(p -> {
            if (p.desconto().compareTo(new BigDecimal(0)) == -1) {
                throw new ValorDescontoInvalidoException();
            }
        });
    }
}
