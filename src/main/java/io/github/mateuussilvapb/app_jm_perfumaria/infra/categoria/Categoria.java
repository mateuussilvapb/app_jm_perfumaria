package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria;

import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_categoria")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Categoria extends CreateAuditableEntity {

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;
}
