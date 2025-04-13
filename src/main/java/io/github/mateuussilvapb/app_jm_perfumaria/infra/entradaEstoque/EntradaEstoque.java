package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque;

import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_entrada_estoque")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntradaEstoque extends CreateAuditableEntity {

    //Propriedades
    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "A situação é obrigatória")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    //Relacionamentos
    @OneToMany(mappedBy = "entradaEstoque", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created_at ASC")
    private List<ProdutoEntradaEstoque> entradasProdutos;

}
