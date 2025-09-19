package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto.ProdutoSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;

public interface IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDto {
    ProdutoSaidaEstoqueResponseDto toDto(ProdutoSaidaEstoque produtoSaidaEstoque);
}
