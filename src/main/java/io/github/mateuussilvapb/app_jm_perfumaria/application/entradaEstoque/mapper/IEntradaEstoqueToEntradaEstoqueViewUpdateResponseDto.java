package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;

public interface IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto {
    EntradaEstoqueToViewUpdateResponseDto toDto(EntradaEstoque entradaEstoque);

}
