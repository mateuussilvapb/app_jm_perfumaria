package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoAutocompleteMovimentacaoEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoAutocompleteMovimentacaoEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoToProdutoAutocompleteMovimentacaoEstoqueDTOImpl implements IProdutoToProdutoAutocompleteMovimentacaoEstoqueDTO {

    @Override
    public ProdutoAutocompleteMovimentacaoEstoqueDTO toDto(Produto produto) {
        return new ProdutoAutocompleteMovimentacaoEstoqueDTO(produto.getIdString(), produto.getNome(), produto.getQuantidadeEmEstoque(), produto.getPrecoCusto(), produto.getPrecoVenda());
    }
}
