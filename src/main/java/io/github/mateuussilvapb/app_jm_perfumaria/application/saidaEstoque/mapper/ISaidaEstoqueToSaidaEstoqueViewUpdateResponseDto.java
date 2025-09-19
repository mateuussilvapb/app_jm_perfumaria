package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

public interface ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto {
    SaidaEstoqueToViewUpdateResponseDto toDto(SaidaEstoque saidaEstoque);

}
