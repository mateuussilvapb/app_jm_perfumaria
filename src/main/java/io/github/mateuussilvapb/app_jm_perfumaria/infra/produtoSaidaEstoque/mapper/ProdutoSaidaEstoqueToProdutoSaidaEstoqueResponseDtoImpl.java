package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto.ProdutoSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDtoImpl implements IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDto {
    private final IProdutoToProdutoResponseDto produtoMapper;

    @Override
    public ProdutoSaidaEstoqueResponseDto toDto(ProdutoSaidaEstoque produtoSaidaEstoque) {
        return new ProdutoSaidaEstoqueResponseDto(
                produtoSaidaEstoque.getId(),
                produtoSaidaEstoque.getIdString(),
                produtoSaidaEstoque.getCreatedAt(),
                produtoSaidaEstoque.getCreatedBy(),
                produtoSaidaEstoque.getStatus(),
                produtoSaidaEstoque.getPrecoUnitario(),
                produtoSaidaEstoque.getQuantidade(),
                produtoSaidaEstoque.getDesconto(),
                produtoMapper.toDto(produtoSaidaEstoque.getProduto())
        );
    }
}
