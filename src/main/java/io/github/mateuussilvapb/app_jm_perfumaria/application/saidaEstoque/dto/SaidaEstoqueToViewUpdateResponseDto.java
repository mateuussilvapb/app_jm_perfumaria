package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto.ProdutoSaidaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record SaidaEstoqueToViewUpdateResponseDto
        (
                Long id,
                String idString,
                LocalDateTime createdAt,
                String createdBy,
                Status status,
                Situacao situacao,
                String descricao,
                Long codigo,
                LocalDate dataSaidaEstoque,
                List<ProdutoSaidaEstoqueToViewUpdateResponseDto> saidasProdutos
        ) {
}
