package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions;

public class ValorDescontoInvalidoException extends RuntimeException {
    public ValorDescontoInvalidoException() {
        super("O valor de desconto para um ou mais produtos informados é inválido. O valor " +
                "precisa ser maior ou igual a 0");
    }
}
