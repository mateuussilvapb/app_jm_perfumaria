package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;

public interface IProdutoSaidaEstoqueDTOtoProdutoSaidaEstoque {
    ProdutoSaidaEstoque toEntity(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDto, Produto produto,
                                   SaidaEstoque saidaEstoque);
}
