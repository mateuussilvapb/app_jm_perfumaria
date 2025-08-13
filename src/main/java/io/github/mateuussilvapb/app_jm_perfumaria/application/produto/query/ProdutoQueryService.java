package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteIdStringDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoFiltersDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.specification.ProdutoSpecification;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoQueryService {

    private final IProdutoRepository produtoRepository;

    public List<Produto> findAllAtivos() {
        return this.produtoRepository.findAllByStatus(Status.ATIVO);
    }

    public List<AutocompleteIdStringDTO> findAllAtivosAutocomplete() {
        return this.findAllAtivos().stream().map(produto -> new AutocompleteIdStringDTO(produto.getIdString(), produto.getNome())).toList();
    }

    public List<AutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm, Status status) {
        return this.produtoRepository.findAllByStatusAndTermoAutocompleteDTO(searchTerm, status);
    }

    public Produto findById(Long id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public Page<Produto> findByFilters(ProdutoFiltersDTO filtros, Pageable pageable) {
        return produtoRepository.findAll(ProdutoSpecification.filtrar(filtros), pageable);
    }

    public List<Produto> findByCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> findByMarca(Marca marca) {
        return produtoRepository.findByMarca(marca);
    }
}
