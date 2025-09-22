package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;

public interface IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto {
    MovimentacaoEstoqueToViewUpdateResponseDto toDto(EntradaEstoque entradaEstoque);

}
