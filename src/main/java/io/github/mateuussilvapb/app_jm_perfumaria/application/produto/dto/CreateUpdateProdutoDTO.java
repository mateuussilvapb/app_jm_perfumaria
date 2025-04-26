package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.math.BigDecimal;

public record CreateUpdateProdutoDTO(
        String nome,
        String descricao,
        BigDecimal precoCusto,
        BigDecimal precoVenda,
        Status status,
        Situacao situacao,
        Long idCategoria,
        Long idMarca
) {
}
