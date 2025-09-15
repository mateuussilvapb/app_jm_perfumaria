package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class QuantidadeEstoqueMaiorQueZeroException extends RuntimeException {
    public QuantidadeEstoqueMaiorQueZeroException(String nome) {
        super("O produto: '" + nome + "' não pode ser desabilitado, pois a sua quantidade em " +
                "estoque é maior que 0.");
    }
}
