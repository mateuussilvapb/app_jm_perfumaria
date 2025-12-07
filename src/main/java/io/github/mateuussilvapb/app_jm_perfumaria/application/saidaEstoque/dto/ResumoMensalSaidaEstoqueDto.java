package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto;

public record ResumoMensalSaidaEstoqueDto(
        Integer ano,
        Integer mes,
        Long quantidadeSaidas,
        Long quantidadeTotal
) {
}
