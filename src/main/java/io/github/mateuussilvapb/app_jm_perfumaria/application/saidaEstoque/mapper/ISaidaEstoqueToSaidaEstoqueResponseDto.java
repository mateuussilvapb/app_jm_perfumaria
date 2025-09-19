package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

public interface ISaidaEstoqueToSaidaEstoqueResponseDto {
    SaidaEstoqueResponseDto toDto(SaidaEstoque saidaEstoque);

}
