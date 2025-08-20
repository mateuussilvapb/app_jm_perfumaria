package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.mapper.ICategoriaToCategoriaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.mapper.IMarcaToMarcaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoToProdutoResponseDtoImpl implements IProdutoToProdutoResponseDto {

    private final IMarcaToMarcaResponseDto marcaMapper;
    private final ICategoriaToCategoriaResponseDto categoriaMapper;

    @Override
    public ProdutoResponseDto toDto(Produto produto) {
        return new ProdutoResponseDto(
                produto.getId(),
                produto.getIdString(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPrecoCusto(),
                produto.getPrecoVenda(),
                produto.getStatus(),
                produto.getCodigo(),
                produto.getQuantidadeEmEstoque(),
                marcaMapper.toDto(produto.getMarca()),
                categoriaMapper.toDto(produto.getCategoria())
        );
    }
}
