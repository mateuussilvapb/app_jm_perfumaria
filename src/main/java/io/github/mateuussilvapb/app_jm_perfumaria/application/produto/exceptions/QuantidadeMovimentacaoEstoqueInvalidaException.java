package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class QuantidadeMovimentacaoEstoqueInvalidaException extends RuntimeException{
    public QuantidadeMovimentacaoEstoqueInvalidaException(String nome) {
        super("A quantidade informada para o produto '" + nome +"' é inválida. A quantidade " +
                "precisa ser maior que 0");
    }
}
