package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseDtoImpl implements IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseDto {
    private final IProdutoToProdutoResponseDto produtoMapper;

    @Override
    public ProdutoEntradaEstoqueResponseDto toDto(ProdutoEntradaEstoque produtoEntradaEstoque) {
        return new ProdutoEntradaEstoqueResponseDto(
                produtoEntradaEstoque.getId(),
                produtoEntradaEstoque.getIdString(),
                produtoEntradaEstoque.getCreatedAt(),
                produtoEntradaEstoque.getCreatedBy(),
                produtoEntradaEstoque.getStatus(),
                produtoEntradaEstoque.getPrecoUnitario(),
                produtoEntradaEstoque.getQuantidade(),
                produtoEntradaEstoque.getDesconto(),
                produtoMapper.toDto(produtoEntradaEstoque.getProduto())
        );
    }
}
