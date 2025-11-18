package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueDTOtoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntradaEstoqueDTOtoEntradaEstoqueImpl implements IEntradaEstoqueDTOtoEntradaEstoque {

    @Override
    public EntradaEstoque toEntity(MovimentacaoEstoqueCreateUpdateDTO entradaEstoqueDTO, Long codigo, List<ProdutoEntradaEstoque> produtos) {
        return new EntradaEstoque(entradaEstoqueDTO.status(), entradaEstoqueDTO.situacao(), entradaEstoqueDTO.descricao(), codigo, entradaEstoqueDTO.dataMovimentacaoEstoque(), produtos);
    }
}
