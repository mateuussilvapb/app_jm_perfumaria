package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.ProdutoFiltersDTO;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ProdutoSpecification {
    public static Specification<Produto> filtrar(ProdutoFiltersDTO filtros) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(filtros.nome())) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + filtros.nome().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(filtros.descricao())) {
                predicates.add(cb.like(cb.lower(root.get("descricao")), "%" + filtros.descricao().toLowerCase() + "%"));
            }

            if (filtros.precoCustoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("precoCusto"), filtros.precoCustoMin()));
            }

            if (filtros.precoCustoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("precoCusto"), filtros.precoCustoMax()));
            }

            if (filtros.precoVendaMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("precoVenda"), filtros.precoVendaMin()));
            }

            if (filtros.precoVendaMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("precoVenda"), filtros.precoVendaMax()));
            }

            if (filtros.status() != null) {
                predicates.add(cb.equal(root.get("status"), filtros.status()));
            }

            if (filtros.situacao() != null) {
                predicates.add(cb.equal(root.get("situacao"), filtros.situacao()));
            }

            if (filtros.idCategoria() != null) {
                predicates.add(cb.equal(root.get("categoria").get("id"), filtros.idCategoria()));
            }

            if (filtros.idMarca() != null) {
                predicates.add(cb.equal(root.get("marca").get("id"), filtros.idMarca()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
