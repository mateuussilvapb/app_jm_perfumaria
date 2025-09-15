package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class PrecoCustoNegativoException extends RuntimeException {
    public PrecoCustoNegativoException() {
        super("O preço de custo informado é menor que 0.");
    }
}
