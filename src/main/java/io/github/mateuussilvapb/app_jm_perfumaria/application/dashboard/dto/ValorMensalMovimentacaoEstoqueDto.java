package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

public record ValorMensalMovimentacaoEstoqueDto(
        Integer ano,
        Integer mes,
        Long valorTotal,
        Long descontoTotal,
        Integer quantidadeTotal
) {
}
