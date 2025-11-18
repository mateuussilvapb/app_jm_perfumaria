package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoAutocompleteMovimentacaoEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;

public interface IProdutoToProdutoAutocompleteMovimentacaoEstoqueDTO {
    ProdutoAutocompleteMovimentacaoEstoqueDTO toDto(Produto produto);
}
