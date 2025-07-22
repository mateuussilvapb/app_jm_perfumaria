package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions;

import java.util.List;

public class CategoriaInUseException extends RuntimeException {
    public CategoriaInUseException(String nomeCategoria, List<String> nomesProdutos) {
        super(buildMessage(nomeCategoria, nomesProdutos));
    }

    private static String buildMessage(String nomeCategoria, List<String> nomesProdutos) {
        StringBuilder message = new StringBuilder();
        message.append("Categoria '")
                .append(nomeCategoria)
                .append("' não pode ser deletada. Ela está vinculada aos seguintes produtos:\n");

        nomesProdutos.forEach(produto -> message.append("- ").append(produto).append("\n"));

        return message.toString().trim();
    }
}