package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque;


import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_produto_entrada_estoque")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProdutoEntradaEstoque extends CreateAuditableEntity {

    //Propriedades
    @Column(name = "preco_unitario", nullable = false)
    @NotNull(message = "O preço por unidade é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço por unidade deve ser acima ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O preço por unidade deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal precoUnitario;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade não pode ser negativa")
    @Max(value = 9999, message = "A quantidade não pode ser maior que 9999")
    private Integer quantidade;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;

    //Relacionamentos
    @NotNull(message = "O produto é obrigatório")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @NotNull(message = "A entrada de estoque é obrigatória")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "entrada_estoque_id", nullable = false)
    private EntradaEstoque entradaEstoque;
}
