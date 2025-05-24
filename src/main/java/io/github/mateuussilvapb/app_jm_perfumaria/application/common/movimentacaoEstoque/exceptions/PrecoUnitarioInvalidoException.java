package io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions;

public class PrecoUnitarioInvalidoException extends RuntimeException {
    public PrecoUnitarioInvalidoException() {
        super("O preço unitário informado para o produto é inválido. O valor precisa ser maior que 0.");
    }
}
