package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueCreateUptadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.util.List;

public record EntradaEstoqueCreateUpdateDTO(
        String descricao,
        Situacao situacao,
        Status status,
        List<ProdutoEntradaEstoqueCreateUptadeDTO> produtos
) {
}