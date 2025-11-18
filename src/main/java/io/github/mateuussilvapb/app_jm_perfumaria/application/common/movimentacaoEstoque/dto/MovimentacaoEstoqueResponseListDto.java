package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MovimentacaoEstoqueResponseListDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                Status status,
                Situacao situacao,
                String descricao,
                Long codigo,
                LocalDate dataMovimentacaoEstoque,
                Integer qtdItensUnicos,
                Integer qtdItens
        ) {
}
