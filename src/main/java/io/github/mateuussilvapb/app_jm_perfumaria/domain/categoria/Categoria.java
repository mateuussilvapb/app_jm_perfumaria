package io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_categoria")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Categoria extends CreateAuditableEntity {

    //Propriedades
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true)
    private String nome;

    @Size(max = 1000)
    private String descricao;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;

    //Relacionamentos
    @OneToMany(mappedBy = "categoria")
    @JsonBackReference
    private List<Produto> produtos;

    //Métodos
    public boolean matchSearchTerm(String searchTerm) {
        if (StringUtils.isBlank(searchTerm)) {
            return false;
        }
        return getNome().toLowerCase().contains(searchTerm.toLowerCase())
                || (getDescricao() != null && getDescricao().toLowerCase().contains(searchTerm.toLowerCase()));
    }
}
