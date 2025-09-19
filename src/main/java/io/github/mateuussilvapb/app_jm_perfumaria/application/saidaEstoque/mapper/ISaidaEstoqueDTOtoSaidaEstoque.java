package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;

import java.util.List;

public interface ISaidaEstoqueDTOtoSaidaEstoque {
    SaidaEstoque toEntity(MovimentacaoEstoqueCreateUpdateDTO saidaEstoqueDTO, Long codigo, List<ProdutoSaidaEstoque> produtos);

}
