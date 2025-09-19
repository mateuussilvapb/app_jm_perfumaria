package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

public record ProdutoSaidaEstoqueFiltersDTO(
        BigDecimal precoUnitarioMin,
        BigDecimal precoUnitarioMax,
        Integer quantidadeMin,
        Integer quantidadeMax,
        Status status,
        BigDecimal descontoMin,
        BigDecimal descontoMax,
        Long idProduto,
        Date dataMin,
        Date dataMax
) {
}
