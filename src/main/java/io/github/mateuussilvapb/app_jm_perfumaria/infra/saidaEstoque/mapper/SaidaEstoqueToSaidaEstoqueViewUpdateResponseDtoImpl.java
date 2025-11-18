package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaidaEstoqueToSaidaEstoqueViewUpdateResponseDtoImpl implements ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto {

    private final IProdutoSaidaEstoqueToProdutoSaidaEstoqueViewUpdateResponseDto produtoSaidaEstoqueMapper;

    @Override
    public MovimentacaoEstoqueToViewUpdateResponseDto toDto(SaidaEstoque saidaEstoque) {
        return new MovimentacaoEstoqueToViewUpdateResponseDto(
                saidaEstoque.getId(),
                saidaEstoque.getIdString(),
                saidaEstoque.getCreatedAt(),
                saidaEstoque.getCreatedBy(),
                saidaEstoque.getStatus(),
                saidaEstoque.getSituacao(),
                saidaEstoque.getDescricao(),
                saidaEstoque.getCodigo(),
                saidaEstoque.getDataSaidaEstoque(),
                saidaEstoque.getSaidasProdutos().stream().map(produtoSaidaEstoqueMapper::toDto).toList()
        );
    }
}
