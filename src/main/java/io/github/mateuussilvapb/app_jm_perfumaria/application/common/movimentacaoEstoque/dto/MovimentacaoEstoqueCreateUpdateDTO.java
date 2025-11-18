package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.time.LocalDate;
import java.util.List;

public record MovimentacaoEstoqueCreateUpdateDTO(
        String descricao,
        Situacao situacao,
        Status status,
        LocalDate dataMovimentacaoEstoque,
        List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos
) {

}
