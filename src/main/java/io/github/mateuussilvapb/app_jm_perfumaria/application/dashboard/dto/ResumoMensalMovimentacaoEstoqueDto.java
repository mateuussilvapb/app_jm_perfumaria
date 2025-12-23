package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

public record ResumoMensalMovimentacaoEstoqueDto(
        Integer ano,
        Integer mes,
        Long quantidadeSaidas,
        Long quantidadeTotal
) {
}
