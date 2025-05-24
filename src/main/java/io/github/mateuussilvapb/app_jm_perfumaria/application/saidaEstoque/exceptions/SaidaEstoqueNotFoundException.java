package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.exceptions;

public class SaidaEstoqueNotFoundException extends RuntimeException {
    public SaidaEstoqueNotFoundException(Long id) {
        super("Saida de estoque com o id: " + id + " não encontrada");
    }
}
