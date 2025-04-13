package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque;

import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.ProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_saida_estoque")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaidaEstoque extends CreateAuditableEntity {

    //Propriedades
    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "A situação é obrigatória")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Size(max = 1000)
    private String descricao;

    //Relacionamentos
    @OneToMany(mappedBy = "saidaEstoque", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created_at ASC")
    private List<ProdutoSaidaEstoque> saidasProdutos;
}
