package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions;

public class DelecaoNaoPermitidaQtdEstoqueInsuficienteException extends RuntimeException {
    public DelecaoNaoPermitidaQtdEstoqueInsuficienteException(String nome, Integer qtdEstoque,
                                                              Integer qtdComprada) {
        super("Remoção não permitida. \nO produto '" + nome +"' tem " + qtdEstoque + " em estoque" +
                ". \nA quantidade comprada na entrada de estoque é de " +qtdComprada + ".\nA " +
                "remoção acarretaria em uma quantidade de estoque negativa.");
    }
}
