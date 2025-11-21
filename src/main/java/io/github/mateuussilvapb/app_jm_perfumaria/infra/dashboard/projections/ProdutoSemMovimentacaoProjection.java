package io.github.mateuussilvapb.app_jm_perfumaria.infra.dashboard.projections;

import java.time.LocalDate;

public interface ProdutoSemMovimentacaoProjection {

    Long getId();
    String getNome();
    String getCategoria();
    String getMarca();
    Integer getQuantidadeEmEstoque();
    LocalDate getDataUltimaSaida();
    Integer getDiasSemMovimentacao();
}
