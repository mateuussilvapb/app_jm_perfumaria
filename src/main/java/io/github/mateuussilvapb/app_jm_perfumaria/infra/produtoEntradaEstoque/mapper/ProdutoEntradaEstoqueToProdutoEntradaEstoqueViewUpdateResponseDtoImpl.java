package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.mapper.IAutocompleteMapper;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoEntradaEstoqueToProdutoEntradaEstoqueViewUpdateResponseDtoImpl implements IProdutoEntradaEstoqueToProdutoEntradaEstoqueViewUpdateResponseDto {
    private final IAutocompleteMapper<Produto> autocompleteMapper;

    @Override
    public ProdutoEntradaEstoqueToViewUpdateResponseDto toDto(ProdutoEntradaEstoque produtoEntradaEstoque) {
        return new ProdutoEntradaEstoqueToViewUpdateResponseDto(
                produtoEntradaEstoque.getId(),
                produtoEntradaEstoque.getIdString(),
                produtoEntradaEstoque.getCreatedAt(),
                produtoEntradaEstoque.getCreatedBy(),
                produtoEntradaEstoque.getPrecoUnitario(),
                produtoEntradaEstoque.getQuantidade(),
                produtoEntradaEstoque.getDesconto(),
                autocompleteMapper.toDto(produtoEntradaEstoque.getProduto())
        );
    }
}
