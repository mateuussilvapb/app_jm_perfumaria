package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SaidaEstoqueResponseListDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                Status status,
                Situacao situacao,
                String descricao,
                Long codigo,
                LocalDate dataSaidaEstoque,
                Integer qtdItensUnicos,
                Integer qtdItens
        ) {
}
