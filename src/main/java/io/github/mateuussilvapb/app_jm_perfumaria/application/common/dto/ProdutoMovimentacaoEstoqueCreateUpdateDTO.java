package io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;

public record ProdutoMovimentacaoEstoqueCreateUpdateDTO(
        String idProduto,
        BigDecimal precoUnitario,
        Integer quantidade,
        Status status,
        BigDecimal desconto
) {
}
