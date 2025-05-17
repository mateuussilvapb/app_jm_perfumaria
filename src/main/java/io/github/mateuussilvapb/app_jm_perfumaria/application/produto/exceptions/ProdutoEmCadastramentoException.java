package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions;

public class ProdutoEmCadastramentoException extends RuntimeException {
    public ProdutoEmCadastramentoException(String nome) {
        super("Produto com o nome '" + nome + "' já está em cadastramento.");
    }
}
