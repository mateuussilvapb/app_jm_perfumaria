package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.time.LocalDateTime;

public record EntradaEstoqueResponseListDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                Status status,
                Situacao situacao,
                String descricao,
                Long codigo,
                Integer qtdItens
        ) {
}
