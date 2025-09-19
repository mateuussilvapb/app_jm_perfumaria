package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto.ProdutoSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseListDtoImpl implements IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseListDto {
    private final IProdutoToProdutoResponseDto produtoMapper;

    @Override
    public ProdutoSaidaEstoqueResponseListDto toDto(ProdutoSaidaEstoque produtoSaidaEstoque) {
        return new ProdutoSaidaEstoqueResponseListDto(
                produtoSaidaEstoque.getId(),
                produtoSaidaEstoque.getIdString(),
                produtoSaidaEstoque.getStatus(),
                produtoSaidaEstoque.getPrecoUnitario(),
                produtoSaidaEstoque.getQuantidade(),
                produtoSaidaEstoque.getDesconto(),
                produtoMapper.toDto(produtoSaidaEstoque.getProduto())
        );
    }
}
