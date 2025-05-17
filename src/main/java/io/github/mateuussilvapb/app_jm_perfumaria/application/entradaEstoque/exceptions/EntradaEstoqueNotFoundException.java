package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions;

public class EntradaEstoqueNotFoundException extends RuntimeException {
    public EntradaEstoqueNotFoundException(Long id) {
        super("Entrada de estoque com o id: " + id + " não encontrada");
    }
}
