package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para retornar o valor total do estoque
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorTotalEstoqueDTO {

    /**
     * Valor total do estoque (precoCusto * quantidadeEmEstoque)
     */
    private BigDecimal valorTotalCusto;

    /**
     * Valor total potencial de venda (precoVenda * quantidadeEmEstoque)
     */
    private BigDecimal valorTotalVenda;

    /**
     * Lucro potencial (valorTotalVenda - valorTotalCusto)
     */
    private BigDecimal lucroPotencial;

    /**
     * Margem de lucro potencial (em porcentagem)
     */
    private BigDecimal margemLucroPotencial;

    /**
     * Quantidade total de produtos em estoque
     */
    private Long quantidadeTotalProdutos;

    /**
     * Quantidade de itens diferentes no estoque
     */
    private Long quantidadeItensDiferentes;
}
