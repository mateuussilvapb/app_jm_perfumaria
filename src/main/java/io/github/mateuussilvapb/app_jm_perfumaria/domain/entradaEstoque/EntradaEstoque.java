package io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

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

    @Size(max = 1000)
    private String descricao;

    @Column(name = "codigo", nullable = false, unique = true, updatable = false)
    private Long codigo;

    //Relacionamentos
    @OneToMany(mappedBy = "entradaEstoque", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created_at ASC")
    @JsonManagedReference
    private List<ProdutoEntradaEstoque> entradasProdutos;

    //Demais métodos
    public boolean matchSearchTerm(String searchTerm) {
        if (StringUtils.isBlank(searchTerm)) {
            return false;
        }
        return (getDescricao() != null && getDescricao().toLowerCase().contains(searchTerm.toLowerCase())) || (getCodigo() != null && getCodigo() == (Long.parseLong(searchTerm)));
    }

}
