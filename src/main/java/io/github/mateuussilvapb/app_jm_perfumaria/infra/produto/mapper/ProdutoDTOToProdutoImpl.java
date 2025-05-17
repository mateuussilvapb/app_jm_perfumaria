package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query.CategoriaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoDTOToProduto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProdutoDTOToProdutoImpl implements IProdutoDTOToProduto {

    private final CategoriaQueryService categoriaQueryService;
    private final MarcaQueryService marcaQueryService;

    @Override
    public Produto toEntity(CreateUpdateProdutoDTO produto) {
        var categoria = categoriaQueryService.findById(produto.idCategoria());
        var marca = marcaQueryService.findById(produto.idMarca());

        return new Produto(produto.nome(), produto.descricao(), produto.precoCusto(),
                produto.precoVenda(), produto.status(), produto.situacao(), 0L, marca, categoria,
                new ArrayList<>(), new ArrayList<>());
    }
}
