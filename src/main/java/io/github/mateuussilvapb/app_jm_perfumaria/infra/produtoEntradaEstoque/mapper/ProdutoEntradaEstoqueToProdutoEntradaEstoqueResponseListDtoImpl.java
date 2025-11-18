package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseListDtoImpl implements IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseListDto {
    private final IProdutoToProdutoResponseDto produtoMapper;

    @Override
    public ProdutoMovimentacaoEstoqueResponseListDto toDto(ProdutoEntradaEstoque produtoEntradaEstoque) {
        return new ProdutoMovimentacaoEstoqueResponseListDto(
                produtoEntradaEstoque.getId(),
                produtoEntradaEstoque.getIdString(),
                produtoEntradaEstoque.getStatus(),
                produtoEntradaEstoque.getPrecoUnitario(),
                produtoEntradaEstoque.getQuantidade(),
                produtoEntradaEstoque.getDesconto(),
                produtoMapper.toDto(produtoEntradaEstoque.getProduto())
        );
    }
}
