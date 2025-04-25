package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.CreateUpdateProdutoDTO;

public interface ProdutoToProdutoDTO {
    CreateUpdateProdutoDTO toDto(Produto produto);
}
