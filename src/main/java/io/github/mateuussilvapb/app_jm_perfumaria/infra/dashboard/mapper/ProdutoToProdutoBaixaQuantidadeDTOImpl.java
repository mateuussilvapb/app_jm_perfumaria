package io.github.mateuussilvapb.app_jm_perfumaria.infra.dashboard.mapper;

import org.springframework.stereotype.Component;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutosBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.mapper.IProdutoToProdutoBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;

@Component
public class ProdutoToProdutoBaixaQuantidadeDTOImpl implements IProdutoToProdutoBaixaQuantidadeDTO {
    @Override
    public ProdutosBaixaQuantidadeDTO toDto(Produto produto) {
        return new ProdutosBaixaQuantidadeDTO(
                produto.getIdString(),
                produto.getNome(),
                produto.getQuantidadeEmEstoque());
    }

}
