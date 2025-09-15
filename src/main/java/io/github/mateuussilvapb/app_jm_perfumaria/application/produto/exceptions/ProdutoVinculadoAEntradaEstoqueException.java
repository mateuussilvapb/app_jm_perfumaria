package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

import java.util.List;

public class ProdutoVinculadoAEntradaEstoqueException extends RuntimeException {
    public ProdutoVinculadoAEntradaEstoqueException(String nomeProduto, List<Long> codigosEntradas) {
        super(buildMessage(nomeProduto, codigosEntradas));
    }

    private static String buildMessage(String nomeProduto, List<Long> codigosEntradas) {
        StringBuilder message = new StringBuilder();
        message.append("Não é possível excluir o produto '");
        message.append(nomeProduto);
        message.append("', pois ele está vinculado à(s) seguinte(s) entrada(s) de estoque:\n");
        codigosEntradas.forEach(ce -> message.append("- ").append(ce.toString()).append("\n"));
        message.append("Exclua essas entradas para poder remover o produto.");
        return message.toString().trim();
    }
}