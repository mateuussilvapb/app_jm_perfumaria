package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record SaidaEstoqueFilterDto(
        LocalDate dataInicial,
        LocalDate dataFinal,
        String descricao,
        List<Long> idsProdutos,
        List<Long> idsMarcas,
        List<Long> idsCategorias,
        BigDecimal descontoMin,
        BigDecimal descontoMax,
        Situacao situacao
) {
}
