package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteIdStringDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoSaidaEstoqueToViewUpdateResponseDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                BigDecimal precoUnitario,
                Integer quantidade,
                BigDecimal desconto,
                AutocompleteIdStringDTO produto
        ) {
}
