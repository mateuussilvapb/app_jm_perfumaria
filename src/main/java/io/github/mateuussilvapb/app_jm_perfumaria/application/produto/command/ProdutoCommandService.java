package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query.CategoriaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.*;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoDTOToProduto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.Constants;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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
        this.update(id, produtoToProdutoDTOMapper.toDto(produto));
        produtoRepository.deleteById(produto.getId());
    }

    public Produto update(Long id, CreateUpdateProdutoDTO updatedProduto) {
        //Validações
        validatePrecoCustoMaiorPrecoVenda(updatedProduto);
        validateProdutoSameName(updatedProduto, id);
        validateValoresMonetarios(updatedProduto);

        var produto = produtoQueryService.findById(id);
        var categoria = categoriaQueryService.findById(updatedProduto.idCategoria());
        var marca = marcaQueryService.findById(updatedProduto.idMarca());

        produto.setNome(updatedProduto.nome());
        produto.setDescricao(updatedProduto.descricao());
        produto.setPrecoCusto(updatedProduto.precoCusto());
        produto.setPrecoVenda(updatedProduto.precoVenda());
        produto.setStatus(updatedProduto.status());
        produto.setCategoria(categoria);
        produto.setMarca(marca);

        return produtoRepository.save(produto);
    }

    public Produto create(CreateUpdateProdutoDTO createdProduto) {
        //Validações
        validatePrecoCustoMaiorPrecoVenda(createdProduto);
        validateProdutoSameName(createdProduto);
        validateValoresMonetarios(createdProduto);

        var produto = produtoDTOToProdutoMapper.toEntity(createdProduto);
        produto.setCodigo(sequenceService.getNextValue(Constants.SEQ_PRODUTO));
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
        //Validações
        validateInativarProdutoComEstoque(produto);

        Status statusAtual = produto.getStatus();
        if (statusAtual.equals(Status.ATIVO)) {
            produto.setStatus(Status.INATIVO);
        } else {
            produto.setStatus(Status.ATIVO);
        }

        return produtoRepository.save(produto);
    }

    private void validatePrecoCustoMaiorPrecoVenda(CreateUpdateProdutoDTO produto) {
        if (produto.precoCusto().compareTo(produto.precoVenda()) > 0) throw new PrecoCustoMaiorPrecoVendaException(produto.nome());
    }

    private void validateProdutoSameName(CreateUpdateProdutoDTO produto) {
        var produtoSameName = produtoRepository.findByNome(produto.nome());
        if (produtoSameName.isPresent()) throw new ProdutoSameNameException(produtoSameName.get().getNome());
    }

    private void validateProdutoSameName(CreateUpdateProdutoDTO produto, Long id) {
        var produtoSameName = produtoRepository.findByNome(produto.nome());
        if (produtoSameName.isPresent() && !Objects.equals(produtoSameName.get().getId(), id)) throw new ProdutoSameNameException(produtoSameName.get().getNome());
    }

    private void validateValoresMonetarios(CreateUpdateProdutoDTO produtoDTO) {
        //Valida se os valores monetários são negativo
        if (produtoDTO.precoVenda().compareTo(BigDecimal.ZERO) < 0) throw new PrecoVendaNegativoException();
        if (produtoDTO.precoCusto().compareTo(BigDecimal.ZERO) < 0) throw new PrecoCustoNegativoException();
    }

    private void validateInativarProdutoComEstoque(Produto produto) {
        if (produto.getStatus() == Status.ATIVO && produto.getQuantidadeEmEstoque() > 0) throw new QuantidadeEstoqueMaiorQueZeroException(produto.getNome());
    }
}
