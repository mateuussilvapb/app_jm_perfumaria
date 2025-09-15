package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.dto.CategoriaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.dto.MarcaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;

public record ProdutoResponseDto
        (
                Long id,
                String idString,
                String nome,
                String descricao,
                BigDecimal precoCusto,
                BigDecimal precoVenda,
                Status status,
                Long codigo,
                Integer quantidadeEmEstoque,
                MarcaResponseDto marca,
                CategoriaResponseDto categoria
        ) {
}
