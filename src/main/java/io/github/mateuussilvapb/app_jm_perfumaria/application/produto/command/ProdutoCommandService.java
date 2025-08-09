package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaInUseException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query.CategoriaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.EstoqueProdutoInsuficienteException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoEmCadastramentoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.QuantidadeMovimentacaoEstoqueInvalidaException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoDTOToProduto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.Constants;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoCommandService {

    private final IProdutoToProdutoDTO produtoToProdutoDTOMapper;
    private final IProdutoDTOToProduto produtoDTOToProdutoMapper;
    private final ProdutoQueryService produtoQueryService;
    private final CategoriaQueryService categoriaQueryService;
    private final MarcaQueryService marcaQueryService;
    private final IProdutoRepository produtoRepository;
    private final SequenceService sequenceService;

    public void deleteById(Long id) {
        var produto = produtoQueryService.findById(id);
        produto.setStatus(Status.INATIVO);
        produto.setSituacao(Situacao.CADASTRO_FINALIZADO);
        this.update(id, produtoToProdutoDTOMapper.toDto(produto));
        produtoRepository.deleteById(produto.getId());
    }

    public Produto update(Long id, CreateUpdateProdutoDTO updatedProduto) {
        var produto = produtoQueryService.findById(id);
        var categoria = categoriaQueryService.findById(updatedProduto.idCategoria());
        var marca = marcaQueryService.findById(updatedProduto.idMarca());

        produto.setNome(updatedProduto.nome());
        produto.setDescricao(updatedProduto.descricao());
        produto.setPrecoCusto(updatedProduto.precoCusto());
        produto.setPrecoVenda(updatedProduto.precoVenda());
        produto.setStatus(updatedProduto.status());
        produto.setSituacao(updatedProduto.situacao());
        produto.setCategoria(categoria);
        produto.setMarca(marca);

        return produtoRepository.save(produto);
    }

    public Produto create(CreateUpdateProdutoDTO createdProduto, Boolean isRascunho) {
        var produtoSameName = produtoRepository.findByNome(createdProduto.nome());
        if (produtoSameName.isPresent()) {
            if (produtoSameName.get().getStatus() == Status.INATIVO && produtoSameName.get().getSituacao() == Situacao.CADASTRO_FINALIZADO) {
                produtoSameName.get().setStatus(Status.ATIVO);
                return this.update(produtoSameName.get().getId(), produtoToProdutoDTOMapper.toDto(produtoSameName.get()));
            }
            if (produtoSameName.get().getStatus() == Status.ATIVO && produtoSameName.get().getSituacao() == Situacao.EM_CADASTRAMENTO) {
                throw new ProdutoEmCadastramentoException(produtoSameName.get().getNome());
            }
            if (produtoSameName.get().getStatus() == Status.ATIVO && produtoSameName.get().getSituacao() == Situacao.CADASTRO_FINALIZADO) {
                throw new ProdutoSameNameException(produtoSameName.get().getNome());
            }
        }
        var produto = produtoDTOToProdutoMapper.toEntity(createdProduto);
        produto.setCodigo(sequenceService.getNextValue(Constants.SEQ_PRODUTO));
        produto.setSituacao(isRascunho ? Situacao.EM_CADASTRAMENTO : Situacao.CADASTRO_FINALIZADO);
        return produtoRepository.save(produto);
    }

    public void adicionarEstoque(Long idProduto, Integer quantidade) {
        Produto produto = produtoQueryService.findById(idProduto);
        if (quantidade < 1) {
            throw new QuantidadeMovimentacaoEstoqueInvalidaException(produto.getNome());
        }
        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() + quantidade);
        produtoRepository.save(produto);
    }

    public void removerEstoque(Long idProduto, Integer quantidade) {
        Produto produto = produtoQueryService.findById(idProduto);
        if (quantidade < 1) {
            throw new QuantidadeMovimentacaoEstoqueInvalidaException(produto.getNome());
        }
        int novaQuantidade = produto.getQuantidadeEmEstoque() - quantidade;
        if (novaQuantidade < 0) {
            throw new EstoqueProdutoInsuficienteException(produto.getNome(), produto.getQuantidadeEmEstoque());
        }
        produto.setQuantidadeEmEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    public Produto toogleStatus(Long id) {
        var produto = produtoQueryService.findById(id);
        Status statusAtual = produto.getStatus();
        if (statusAtual.equals(Status.ATIVO)) {
            produto.setStatus(Status.INATIVO);
        } else {
            produto.setStatus(Status.ATIVO);
        }

        return produtoRepository.save(produto);
    }
}
