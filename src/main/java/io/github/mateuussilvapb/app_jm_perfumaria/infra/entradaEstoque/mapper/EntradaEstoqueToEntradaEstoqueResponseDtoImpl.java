package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EntradaEstoqueToEntradaEstoqueResponseDtoImpl implements IEntradaEstoqueToEntradaEstoqueResponseDto {

    private final IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseDto produtoEntradaEstoqueMapper;

    @Override
    public MovimentacaoEstoqueResponseDto toDto(EntradaEstoque entradaEstoque) {
        return new MovimentacaoEstoqueResponseDto(
                entradaEstoque.getId(),
                entradaEstoque.getIdString(),
                entradaEstoque.getCreatedAt(),
                entradaEstoque.getCreatedBy(),
                entradaEstoque.getStatus(),
                entradaEstoque.getSituacao(),
                entradaEstoque.getDescricao(),
                entradaEstoque.getCodigo(),
                entradaEstoque.getDataEntradaEstoque(),
                entradaEstoque.getEntradasProdutos().stream().map(produtoEntradaEstoqueMapper::toDto).toList()
        );
    }
}
