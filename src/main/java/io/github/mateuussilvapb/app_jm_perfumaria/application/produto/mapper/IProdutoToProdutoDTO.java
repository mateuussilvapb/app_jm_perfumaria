package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;

public interface IProdutoToProdutoDTO {
    CreateUpdateProdutoDTO toDto(Produto produto);
}
