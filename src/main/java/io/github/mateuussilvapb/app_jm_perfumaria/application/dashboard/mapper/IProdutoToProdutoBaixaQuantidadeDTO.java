package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutosBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;

public interface IProdutoToProdutoBaixaQuantidadeDTO {
    ProdutosBaixaQuantidadeDTO toDto(Produto produto);
}
