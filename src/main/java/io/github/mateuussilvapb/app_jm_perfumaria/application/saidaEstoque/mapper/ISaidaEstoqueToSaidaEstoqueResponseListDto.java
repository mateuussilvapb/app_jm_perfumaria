package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

public interface ISaidaEstoqueToSaidaEstoqueResponseListDto {
    MovimentacaoEstoqueResponseListDto toDto(SaidaEstoque saidaEstoque);

}
