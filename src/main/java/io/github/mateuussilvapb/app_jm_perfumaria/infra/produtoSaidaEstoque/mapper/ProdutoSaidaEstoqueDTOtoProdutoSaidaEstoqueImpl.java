package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueDTOtoProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSaidaEstoqueDTOtoProdutoSaidaEstoqueImpl implements IProdutoSaidaEstoqueDTOtoProdutoSaidaEstoque {
    @Override
    public ProdutoSaidaEstoque toEntity(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDto, Produto produto, SaidaEstoque saidaEstoque) {
        return new ProdutoSaidaEstoque(produtoDto.precoUnitario(), produtoDto.quantidade(), produtoDto.status(), produtoDto.desconto(), produto, saidaEstoque);
    }
}
