package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions;

import java.util.List;

public class MarcaInUseException extends RuntimeException {
    public MarcaInUseException(String nomeMarca, List<String> nomesProdutos) {
        super(buildMessage(nomeMarca, nomesProdutos));
    }

    private static String buildMessage(String nomeMarca, List<String> nomesProdutos) {
        StringBuilder message = new StringBuilder();
        message.append("Marca '")
                .append(nomeMarca)
                .append("' não pode ser deletada/desabilitada. Ela está vinculada aos seguintes " +
                        "produtos:\n");

        nomesProdutos.forEach(produto -> message.append("- ").append(produto).append("\n"));

        return message.toString().trim();
    }
}