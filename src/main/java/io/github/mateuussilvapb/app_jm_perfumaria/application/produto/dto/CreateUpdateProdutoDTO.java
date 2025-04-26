package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateUpdateProdutoDTO(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,
        @NotBlank
        @Size(max = 1000)
        String descricao,
        @NotNull(message = "O preço de custo é obrigatório")
        @DecimalMin(value = "0.00", message = "O preço de custo deve ser acima ou igual a zero")
        @Digits(integer = 10, fraction = 2, message = "O preço deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
        BigDecimal precoCusto,
        @NotNull(message = "O preço de custo é obrigatório")
        @DecimalMin(value = "0.00", message = "O preço de custo deve ser acima ou igual a zero")
        @Digits(integer = 10, fraction = 2, message = "O preço deve ter no máximo 10 dígitos inteiros e 2 casas decimais")
        BigDecimal precoVenda,
        @NotNull(message = "Informe um status")
        Status status,
        @NotNull(message = "Informe uma situação")
        Situacao situacao,
        @NotNull(message = "Informe a categoria")
        Long idCategoria,
        @NotNull(message = "Informe a marca")
        Long idMarca
) {
}
