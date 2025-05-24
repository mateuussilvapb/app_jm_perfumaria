package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;

public interface IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque {
    ProdutoEntradaEstoque toEntity(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDto, Produto produto,
                                   EntradaEstoque entradaEstoque);
}
