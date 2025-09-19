package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

public interface ISaidaEstoqueToSaidaEstoqueResponseListDto {
    SaidaEstoqueResponseListDto toDto(SaidaEstoque saidaEstoque);

}
