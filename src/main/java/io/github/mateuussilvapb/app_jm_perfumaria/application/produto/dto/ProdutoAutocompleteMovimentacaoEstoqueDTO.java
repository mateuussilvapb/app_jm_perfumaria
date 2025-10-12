package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto;

import java.math.BigDecimal;

public record ProdutoAutocompleteMovimentacaoEstoqueDTO(
        String id,
        String nome,
        Integer quantidadeEmEstoque,
        BigDecimal precoCusto,
        BigDecimal precoVenda
) {
}
