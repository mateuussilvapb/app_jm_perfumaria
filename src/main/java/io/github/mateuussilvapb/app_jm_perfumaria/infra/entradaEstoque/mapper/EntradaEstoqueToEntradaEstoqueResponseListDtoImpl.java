package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EntradaEstoqueToEntradaEstoqueResponseListDtoImpl implements IEntradaEstoqueToEntradaEstoqueResponseListDto {


    @Override
    public EntradaEstoqueResponseListDto toDto(EntradaEstoque entradaEstoque) {
        return new EntradaEstoqueResponseListDto(
                entradaEstoque.getId(),
                entradaEstoque.getIdString(),
                entradaEstoque.getCreatedAt(),
                entradaEstoque.getCreatedBy(),
                entradaEstoque.getStatus(),
                entradaEstoque.getSituacao(),
                entradaEstoque.getDescricao(),
                entradaEstoque.getCodigo(),
                entradaEstoque.getDataEntradaEstoque(),
                entradaEstoque.getEntradasProdutos().size(),
                entradaEstoque.getEntradasProdutos().stream().mapToInt(ProdutoEntradaEstoque::getQuantidade).sum()
        );
    }
}
