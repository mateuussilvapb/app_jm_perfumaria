package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaidaEstoqueToSaidaEstoqueResponseDtoImpl implements ISaidaEstoqueToSaidaEstoqueResponseDto {

    private final IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseDto produtoSaidaEstoqueMapper;

    @Override
    public SaidaEstoqueResponseDto toDto(SaidaEstoque saidaEstoque) {
        return new SaidaEstoqueResponseDto(
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
