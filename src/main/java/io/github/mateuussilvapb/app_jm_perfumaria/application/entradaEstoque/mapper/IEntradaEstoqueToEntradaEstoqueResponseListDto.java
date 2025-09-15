package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;

public interface IEntradaEstoqueToEntradaEstoqueResponseListDto {
    EntradaEstoqueResponseListDto toDto(EntradaEstoque entradaEstoque);

}
