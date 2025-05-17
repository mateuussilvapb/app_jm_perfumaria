package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoFiltersDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.specification.ProdutoSpecification;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoNotFoundException;
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

    public List<AutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm, Status status) {
        return this.produtoRepository.findAllByStatusAndTermoAutocompleteDTO(searchTerm, status);
    }

    public Produto findById(Long id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public Page<Produto> findByFilters(ProdutoFiltersDTO filtros, Pageable pageable) {
        return produtoRepository.findAll(ProdutoSpecification.filtrar(filtros), pageable);
    }
}
