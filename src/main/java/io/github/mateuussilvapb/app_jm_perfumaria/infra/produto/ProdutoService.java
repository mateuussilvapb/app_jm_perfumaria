package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto;

import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.ProdutoAutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.dto.ProdutoFiltersDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces.ProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final SequenceService sequenceService;
    private final ProdutoToProdutoDTO atualizarProdutoMapper;

    @Transactional
    public List<Produto> findAllAtivos() {
        return this.produtoRepository.findAllProdutosByStatus(Status.ATIVO);
    }

    @Transactional
    public List<Produto> findAllInativos() {
        return this.produtoRepository.findAllProdutosByStatus(Status.INATIVO);
    }

    @Transactional
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

    @Transactional
    public List<ProdutoAutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm,
                                                                             Status status) {
        return this.produtoRepository.findAllProdutosByStatusAutocompleteDTO(searchTerm, status);
    }

    @Transactional
    public List<Produto> findAllBySituacaoAndStatus(Situacao situacao, Status status) {
        return this.produtoRepository.findAllProdutosBySituacaoAndStatus(situacao, status);
    }

    @Transactional
    public Produto findById(Long id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    @Transactional
    public Page<Produto> findByFilters(ProdutoFiltersDTO filtros, Pageable pageable) {
        return produtoRepository
                .findAll(ProdutoSpecification.filtrar(filtros), pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        var produto = this.findById(id);
        produto.setStatus(Status.INATIVO);
        produto.setSituacao(Situacao.CADASTRO_FINALIZADO);
        this.update(id, atualizarProdutoMapper.toDto(produto));
    }

    @Transactional
    public Produto update(Long id, CreateUpdateProdutoDTO updatedProduto) {
        var produto = this.findById(id);
        //TODO: Necessário implementar service de Categoria e Marca para validação de ids
        return null;
    }

    @Transactional
    public Produto create(CreateUpdateProdutoDTO createdProduto) {
        //TODO: Necessário implementar service de Categoria e Marca para validação de ids
        return null;
    }

}
