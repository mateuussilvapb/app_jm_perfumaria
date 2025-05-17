package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoToProdutoDTOImpl implements IProdutoToProdutoDTO {

    @Override
    public CreateUpdateProdutoDTO toDto(Produto produto) {
        return new CreateUpdateProdutoDTO(produto.getNome(), produto.getDescricao(),
                produto.getPrecoCusto(), produto.getPrecoVenda(), produto.getStatus(),
                produto.getSituacao(), produto.getCategoria().getId(), produto.getMarca().getId());
    }
}
