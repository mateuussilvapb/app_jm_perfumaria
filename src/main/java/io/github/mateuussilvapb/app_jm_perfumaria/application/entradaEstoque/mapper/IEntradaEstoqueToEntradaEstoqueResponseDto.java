package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;

public interface IEntradaEstoqueToEntradaEstoqueResponseDto {
    EntradaEstoqueResponseDto toDto(EntradaEstoque entradaEstoque);

}
