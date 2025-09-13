package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EntradaEstoqueToEntradaEstoqueViewUpdateResponseDtoImpl implements IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto {

    private final IProdutoEntradaEstoqueToProdutoEntradaEstoqueViewUpdateResponseDto produtoEntradaEstoqueMapper;

    @Override
    public EntradaEstoqueToViewUpdateResponseDto toDto(EntradaEstoque entradaEstoque) {
        return new EntradaEstoqueToViewUpdateResponseDto(
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
