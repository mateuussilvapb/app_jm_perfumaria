package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;

public interface IProdutoToProdutoResponseDto {
    ProdutoResponseDto toDto(Produto produto);
}
