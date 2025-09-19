package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query.specification;

import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SaidaEstoqueSpecifications {
    public static Specification<SaidaEstoque> comFiltros(SaidaEstoqueFilterDto filtros) {
        return (root, query, cb) -> {
            // Para evitar duplicatas quando join com produtos/marcas/categorias
            assert query != null;
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            // Filtro por datas
            if (filtros.dataInicial() != null && filtros.dataFinal() != null) {
                predicates.add(cb.between(root.get("dataSaidaEstoque"), filtros.dataInicial(), filtros.dataFinal()));
            } else if (filtros.dataInicial() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataSaidaEstoque"), filtros.dataInicial()));
            } else if (filtros.dataFinal() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataSaidaEstoque"), filtros.dataFinal()));
            }

            // Filtro por descrição
            if (StringUtils.isNotBlank(filtros.descricao())) {
                predicates.add(cb.like(cb.lower(root.get("descricao")), "%" + filtros.descricao().toLowerCase() + "%"));
            }

            // Join com ProdutoSaidaEstoque -> Produto -> Marca -> Categoria
            Join<SaidaEstoque, ProdutoSaidaEstoque> joinPEE = root.join("saidasProdutos", JoinType.LEFT);
            Join<ProdutoSaidaEstoque, Produto> joinProduto = joinPEE.join("produto", JoinType.LEFT);

            // Produtos
            if (filtros.idsProdutos() != null && !filtros.idsProdutos().isEmpty()) {
                predicates.add(joinProduto.get("id").in(filtros.idsProdutos()));
            }

            // Marcas
            if (filtros.idsMarcas() != null && !filtros.idsMarcas().isEmpty()) {
                predicates.add(joinProduto.get("marca").get("id").in(filtros.idsMarcas()));
            }

            // Categorias
            if (filtros.idsCategorias() != null && !filtros.idsCategorias().isEmpty()) {
                predicates.add(joinProduto.get("categoria").get("id").in(filtros.idsCategorias()));
            }

            // Range de desconto
            if (filtros.descontoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(joinPEE.get("desconto"), filtros.descontoMin()));
            }
            if (filtros.descontoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(joinPEE.get("desconto"), filtros.descontoMax()));
            }

            // Situação
            if (filtros.situacao() != null) {
                predicates.add(cb.equal(root.get("situacao"), filtros.situacao()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
