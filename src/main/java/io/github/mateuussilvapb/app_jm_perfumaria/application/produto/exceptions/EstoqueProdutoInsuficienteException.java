package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class EstoqueProdutoInsuficienteException extends RuntimeException{
    public EstoqueProdutoInsuficienteException(String mensagemErro) {
        super(mensagemErro);
    }
}
