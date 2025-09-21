package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.query.specification;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueFiltersDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ProdutoSaidaEstoqueSpecification {
    public static Specification<ProdutoSaidaEstoqueSpecification> filtrar(ProdutoMovimentacaoEstoqueFiltersDTO filtros) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtros.precoUnitarioMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("precoUnitario"), filtros.precoUnitarioMin()));
            }

            if (filtros.precoUnitarioMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("precoUnitario"), filtros.precoUnitarioMax()));
            }

            if (filtros.quantidadeMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("quantidade"), filtros.quantidadeMin()));
            }

            if (filtros.quantidadeMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("quantidade"), filtros.quantidadeMax()));
            }

            if (filtros.status() != null) {
                predicates.add(cb.equal(root.get("status"), filtros.status()));
            }

            if (filtros.descontoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("desconto"), filtros.descontoMin()));
            }

            if (filtros.descontoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("desconto"), filtros.descontoMax()));
            }

            if (filtros.idProduto() != null) {
                predicates.add(cb.equal(root.get("produto").get("id"), filtros.idProduto()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
