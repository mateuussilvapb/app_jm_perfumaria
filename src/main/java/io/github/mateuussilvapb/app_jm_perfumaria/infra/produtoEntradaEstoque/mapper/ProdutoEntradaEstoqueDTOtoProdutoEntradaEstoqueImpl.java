package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import org.springframework.stereotype.Component;

@Component
public class ProdutoEntradaEstoqueDTOtoProdutoEntradaEstoqueImpl implements IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque {
    @Override
    public ProdutoEntradaEstoque toEntity(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDto, Produto produto,
                                          EntradaEstoque entradaEstoque) {
        return new ProdutoEntradaEstoque(produtoDto.precoUnitario(), produtoDto.quantidade(), produtoDto.status(), produtoDto.desconto(), produto, entradaEstoque);
    }
}
