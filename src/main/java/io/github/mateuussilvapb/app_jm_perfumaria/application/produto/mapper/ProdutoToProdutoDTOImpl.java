package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces.IProdutoToProdutoDTO;
import org.springframework.stereotype.Service;

@Service
public class ProdutoToProdutoDTOImpl implements IProdutoToProdutoDTO {

    @Override
    public CreateUpdateProdutoDTO toDto(Produto produto) {
        return new CreateUpdateProdutoDTO(produto.getNome(), produto.getDescricao(),
                produto.getPrecoCusto(), produto.getPrecoVenda(), produto.getStatus(),
                produto.getSituacao(), produto.getCategoria().getId(), produto.getMarca().getId());
    }
}
