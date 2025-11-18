package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.mapper.IAutocompleteMapper;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoSaidaEstoqueToProdutoSaidaEstoqueViewUpdateResponseDtoImpl implements IProdutoSaidaEstoqueToProdutoSaidaEstoqueViewUpdateResponseDto {
    private final IAutocompleteMapper<Produto> autocompleteMapper;

    @Override
    public ProdutoMovimentacaoEstoqueToViewUpdateResponseDto toDto(ProdutoSaidaEstoque produtoSaidaEstoque) {
        return new ProdutoMovimentacaoEstoqueToViewUpdateResponseDto(
                produtoSaidaEstoque.getId(),
                produtoSaidaEstoque.getIdString(),
                produtoSaidaEstoque.getCreatedAt(),
                produtoSaidaEstoque.getCreatedBy(),
                produtoSaidaEstoque.getPrecoUnitario(),
                produtoSaidaEstoque.getQuantidade(),
                produtoSaidaEstoque.getDesconto(),
                autocompleteMapper.toDto(produtoSaidaEstoque.getProduto())
        );
    }
}
