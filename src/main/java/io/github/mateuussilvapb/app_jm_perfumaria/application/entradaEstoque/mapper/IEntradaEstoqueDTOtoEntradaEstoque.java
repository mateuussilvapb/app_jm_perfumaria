package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;

import java.util.List;

public interface IEntradaEstoqueDTOtoEntradaEstoque {
    EntradaEstoque toEntity(MovimentacaoEstoqueCreateUpdateDTO entradaEstoqueDTO, Long codigo,
                            List<ProdutoEntradaEstoque> produtos);

}
