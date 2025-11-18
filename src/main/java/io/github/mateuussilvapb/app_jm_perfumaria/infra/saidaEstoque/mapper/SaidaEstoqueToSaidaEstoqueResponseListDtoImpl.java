package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaidaEstoqueToSaidaEstoqueResponseListDtoImpl implements ISaidaEstoqueToSaidaEstoqueResponseListDto {


    @Override
    public MovimentacaoEstoqueResponseListDto toDto(SaidaEstoque saidaEstoque) {
        return new MovimentacaoEstoqueResponseListDto(
                saidaEstoque.getId(),
                saidaEstoque.getIdString(),
                saidaEstoque.getCreatedAt(),
                saidaEstoque.getCreatedBy(),
                saidaEstoque.getStatus(),
                saidaEstoque.getSituacao(),
                saidaEstoque.getDescricao(),
                saidaEstoque.getCodigo(),
                saidaEstoque.getDataSaidaEstoque(),
                saidaEstoque.getSaidasProdutos().size(),
                saidaEstoque.getSaidasProdutos().stream().mapToInt(ProdutoSaidaEstoque::getQuantidade).sum()
        );
    }
}
