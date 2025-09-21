package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoMovimentacaoEstoqueResponseDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                Status status,
                BigDecimal precoUnitario,
                Integer quantidade,
                BigDecimal desconto,
                ProdutoResponseDto produto
        ) {
}
