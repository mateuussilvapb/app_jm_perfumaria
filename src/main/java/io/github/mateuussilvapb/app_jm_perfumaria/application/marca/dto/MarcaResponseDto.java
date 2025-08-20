package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

public record MarcaResponseDto
        (
                Long id,
                String idString,
                String nome,
                Status status,
                String descricao
        ) {
}
