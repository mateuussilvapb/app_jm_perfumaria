package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

import java.util.List;

public record MovimentacaoEstoqueQuantidadeItensDTO(
  List<ResumoMensalMovimentacaoEstoqueDto> entradaEstoqueItens,
  List<ResumoMensalMovimentacaoEstoqueDto> saidaEstoqueItens
) {
}
