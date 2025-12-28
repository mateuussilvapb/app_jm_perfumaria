package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

import java.util.List;

public record MovimentacaoEstoqueValorItensDTO(
  List<ValorMensalMovimentacaoEstoqueDto> entradaEstoqueValores,
  List<ValorMensalMovimentacaoEstoqueDto> saidaEstoqueValores
) {
}
