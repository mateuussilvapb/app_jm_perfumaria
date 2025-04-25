package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces.ProdutoToProdutoDTO;
import org.springframework.stereotype.Service;

@Service
public class ProdutoToProdutoDTOImpl implements ProdutoToProdutoDTO {

    @Override
    public CreateUpdateProdutoDTO toDto(Produto produto) {
        return new CreateUpdateProdutoDTO(produto.getNome(), produto.getDescricao(),
                produto.getPrecoCusto(), produto.getPrecoVenda(), produto.getStatus(),
                produto.getSituacao(), produto.getCategoria().getId(), produto.getMarca().getId());
    }
}
