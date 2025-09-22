package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

public interface ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto {
    MovimentacaoEstoqueToViewUpdateResponseDto toDto(SaidaEstoque saidaEstoque);

}
