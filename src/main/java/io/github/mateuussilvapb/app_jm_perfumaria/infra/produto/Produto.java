package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto;


import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.CreateAuditableEntity;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.ProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.Referable;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_produto")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Produto extends CreateAuditableEntity implements Referable<String> {

    //Propriedades
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, unique = true)
    private String nome;

    @Size(max = 1000)
    private String descricao;

    @Column(name = "preco_custo", nullable = false)
    @NotNull(message = "O preço de custo é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço de custo deve ser acima ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O preço deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal precoCusto;

    @Column(name = "preco_venda", nullable = false)
    @NotNull(message = "O preço de venda é obrigatório")
    @DecimalMin(value = "0.00", message = "O preço de venda deve ser acima ou igual a zero")
    @Digits(integer = 10, fraction = 2, message = "O preço de venda deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
    private BigDecimal precoVenda;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "A situação é obrigatória")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    //Relacionamentos
    @NotNull(message = "A marca é obrigatória")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoEntradaEstoque> entradasEstoque;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoSaidaEstoque> saidasEstoque;

    //Demais métodos
    @Override
    public String getIdentificacao() {
        return getIdString();
    }
}
