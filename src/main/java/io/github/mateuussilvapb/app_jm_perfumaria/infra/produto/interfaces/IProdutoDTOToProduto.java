package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;

public interface IProdutoDTOToProduto {
    Produto toEntity(CreateUpdateProdutoDTO produto);
}
