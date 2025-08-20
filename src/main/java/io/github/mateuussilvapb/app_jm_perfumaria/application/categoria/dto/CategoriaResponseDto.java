package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

public record CategoriaResponseDto
        (
                Long id,
                String idString,
                String nome,
                Status status,
                String descricao
        ) {
}
