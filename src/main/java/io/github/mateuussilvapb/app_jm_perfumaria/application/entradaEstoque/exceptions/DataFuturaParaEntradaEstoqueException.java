package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions;

public class DataFuturaParaEntradaEstoqueException extends RuntimeException {
    public DataFuturaParaEntradaEstoqueException() {
        super("A data informada não pode ser uma data futura.");
    }
}
