package io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.util.List;

public record MovimentacaoEstoqueCreateUpdateDTO(
        String descricao,
        Situacao situacao,
        Status status,
        List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos
) {

}
