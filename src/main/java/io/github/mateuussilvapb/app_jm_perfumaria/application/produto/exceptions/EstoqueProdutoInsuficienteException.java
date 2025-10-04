package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class EstoqueProdutoInsuficienteException extends RuntimeException{
    public EstoqueProdutoInsuficienteException(String nome, Integer qtdDisponivel) {
        super("Produto com o nome '" + nome + "' não possui estoque suficiente. A quantidade " +
                "disponível em estoque é de " + qtdDisponivel + " produtos");
    }
}
