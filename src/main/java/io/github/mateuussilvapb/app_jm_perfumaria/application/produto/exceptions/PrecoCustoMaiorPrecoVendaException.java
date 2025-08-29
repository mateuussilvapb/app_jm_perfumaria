package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class PrecoCustoMaiorPrecoVendaException extends RuntimeException {
    public PrecoCustoMaiorPrecoVendaException(String nome) {
        super("O produto: '" + nome + "' possui o preço de custo maior que o preço de venda.");
    }
}
