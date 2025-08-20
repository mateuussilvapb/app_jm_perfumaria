package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.util.List;

public record EntradaEstoqueResponseDto
        (
                Long id,
                String idString,
                Status status,
                Situacao situacao,
                String descricao,
                Long codigo,
                List<ProdutoEntradaEstoqueResponseDto> entradasProdutos
        ) {
}
