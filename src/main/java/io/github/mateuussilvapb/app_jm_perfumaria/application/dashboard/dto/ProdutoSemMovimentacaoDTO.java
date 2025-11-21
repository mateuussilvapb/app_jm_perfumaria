package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

import java.time.LocalDate;

public record ProdutoSemMovimentacaoDTO(
        Long id,
        String nome,
        String categoria,
        String marca,
        Integer quantidadeEmEstoque,
        LocalDate dataUltimaSaida,
        Integer diasSemMovimentacao) {
}
