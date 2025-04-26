package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoFiltersDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.specification.ProdutoSpecification;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoQueryService {

    private final IProdutoRepository produtoRepository;

    public List<Produto> findAllAtivos() {
        return this.produtoRepository.findAllByStatus(Status.ATIVO);
    }

    public List<Produto> findAllInativos() {
        return this.produtoRepository.findAllByStatus(Status.INATIVO);
    }

    public List<Produto> findAllByTermAndStatus(String searchTerm, Status status) {
        List<Produto> produtos;

        if (status == Status.ATIVO) {
            produtos = this.findAllAtivos();
        } else {
            produtos = this.findAllInativos();
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            produtos = produtos.stream()
                    .filter(produto -> produto.matchSearchTerm(searchTerm))
                    .collect(Collectors.toList());
        }

        return produtos;
    }

    public List<AutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm,
                                                                      Status status) {
        return this.produtoRepository.findAllByStatusAndTermoAutocompleteDTO(searchTerm, status);
    }

    public List<Produto> findAllBySituacaoAndStatus(Situacao situacao, Status status) {
        return this.produtoRepository.findAllBySituacaoAndStatus(situacao, status);
    }

    public Produto findById(Long id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public Page<Produto> findByFilters(ProdutoFiltersDTO filtros, Pageable pageable) {
        return produtoRepository
                .findAll(ProdutoSpecification.filtrar(filtros), pageable);
    }
}
