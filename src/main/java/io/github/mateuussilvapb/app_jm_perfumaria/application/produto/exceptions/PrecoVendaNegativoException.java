package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class PrecoVendaNegativoException extends RuntimeException {
    public PrecoVendaNegativoException() {
        super("O preço de venda informado é menor que 0.");
    }
}
