package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;

public record ProdutoEntradaEstoqueResponseListDto
        (
                Long id,
                String idString,
                Status status,
                BigDecimal precoUnitario,
                Integer quantidade,
                BigDecimal desconto,
                ProdutoResponseDto produto
        ) {
}
