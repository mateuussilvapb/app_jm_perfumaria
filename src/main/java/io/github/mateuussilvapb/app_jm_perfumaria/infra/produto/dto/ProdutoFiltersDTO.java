package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;

public record ProdutoFiltersDTO(
        String nome,
        String descricao,
        BigDecimal precoCustoMin,
        BigDecimal precoCustoMax,
        BigDecimal precoVendaMin,
        BigDecimal precoVendaMax,
        Status status,
        Situacao situacao,
        Long idCategoria,
        Long idMarca
) {
}
