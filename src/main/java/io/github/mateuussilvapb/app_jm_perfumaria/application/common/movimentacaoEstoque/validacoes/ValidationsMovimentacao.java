package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.validacoes;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.PrecoUnitarioInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.ValorDescontoInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.DataFuturaParaEntradaEstoqueException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueSemProdutosException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ValidationsMovimentacao {

    public static void validateIfProdutosExists(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new EntradaEstoqueSemProdutosException();
        }
    }

    public static void validateIfPrecoLessThenOne(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        produtos.forEach(p -> {
            if (p.precoUnitario().compareTo(new BigDecimal(0)) < 0) {
                throw new PrecoUnitarioInvalidoException();
            }
        });
    }

    public static void validateIfDescontoLessThenOne(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        produtos.forEach(p -> {
            if (p.desconto().compareTo(new BigDecimal(0)) < 0) {
                throw new ValorDescontoInvalidoException();
            }
        });
    }

    public static void validateDateIsInThePast(LocalDate date) {
        LocalDate hoje = LocalDate.now();

        if (date.isAfter(hoje)) {
            throw new DataFuturaParaEntradaEstoqueException();
        }
    }
}
