package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorTotalEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service responsável por consultas do dashboard
 */
@Service
@RequiredArgsConstructor
public class DashboardQueryService {

    private final IProdutoRepository produtoRepository;

    /**
     * Calcula o valor total do estoque e informações relacionadas
     * 
     * @return DTO com valor total em custo, venda, margem de lucro e quantidades
     */
    public ValorTotalEstoqueDTO calcularValorTotalEstoque() {
        // Busca os valores do banco de dados
        BigDecimal valorTotalCusto = produtoRepository.calcularValorTotalEstoqueCusto();
        BigDecimal valorTotalVenda = produtoRepository.calcularValorTotalEstoqueVenda();
        Long quantidadeTotalProdutos = produtoRepository.calcularQuantidadeTotalEstoque();
        Long quantidadeItensDiferentes = produtoRepository.contarProdutosAtivos();

        // Calcula a margem de lucro potencial
        BigDecimal margemLucroPotencial = BigDecimal.ZERO;
        if (valorTotalCusto.compareTo(BigDecimal.ZERO) > 0) {
            margemLucroPotencial = valorTotalVenda.subtract(valorTotalCusto)
                    .divide(valorTotalCusto, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Calcula o lucro potencial
        BigDecimal lucroPotencial = valorTotalVenda.subtract(valorTotalCusto)
                .setScale(2, RoundingMode.HALF_UP);

        return new ValorTotalEstoqueDTO(
                valorTotalCusto,
                valorTotalVenda,
                lucroPotencial,
                margemLucroPotencial,
                quantidadeTotalProdutos,
                quantidadeItensDiferentes);
    }
}