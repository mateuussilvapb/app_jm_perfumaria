package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;

import java.util.List;

public interface IEntradaEstoqueDTOtoEntradaEstoque {
    EntradaEstoque toEntity(EntradaEstoqueCreateUpdateDTO entradaEstoqueDTO, Long codigo, List<ProdutoEntradaEstoque> produtos);

}
