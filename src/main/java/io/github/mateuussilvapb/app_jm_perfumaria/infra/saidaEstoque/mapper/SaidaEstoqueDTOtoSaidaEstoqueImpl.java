package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueDTOtoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaidaEstoqueDTOtoSaidaEstoqueImpl implements ISaidaEstoqueDTOtoSaidaEstoque {

    @Override
    public SaidaEstoque toEntity(MovimentacaoEstoqueCreateUpdateDTO saidaEstoqueDTO, Long codigo, List<ProdutoSaidaEstoque> produtos) {
        return new SaidaEstoque(saidaEstoqueDTO.status(), saidaEstoqueDTO.situacao(), saidaEstoqueDTO.descricao(), codigo, saidaEstoqueDTO.dataMovimentacaoEstoque(), produtos);
    }
}
