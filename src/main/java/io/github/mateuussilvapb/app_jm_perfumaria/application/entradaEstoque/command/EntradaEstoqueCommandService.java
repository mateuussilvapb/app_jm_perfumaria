package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.validacoes.ValidationsMovimentacao;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.DelecaoNaoPermitidaQtdEstoqueInsuficienteException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueDTOtoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command.ProdutoCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.PrecoCustoMaiorPrecoVendaException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository.IEntradaEstoqueRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.Constants;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EntradaEstoqueCommandService {

    private final IEntradaEstoqueRepository entradaEstoqueRepository;
    private final ProdutoQueryService produtoQueryService;
    private final SequenceService sequenceService;
    private final IEntradaEstoqueDTOtoEntradaEstoque entradaEstoqueMapper;
    private final IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque produtoEntradaEstoqueMapper;
    private final IProdutoToProdutoDTO produtoDTOMapper;
    private final ProdutoCommandService produtoCommandService;

    @Transactional
    public EntradaEstoque createEntradaEstoqueComProdutos(MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Validações dos produtos
        ValidationsMovimentacao.validateIfProdutosExists(dto.produtos());
        ValidationsMovimentacao.validateIfPrecoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateIfDescontoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataEntradaEstoque());
        validatePrecoCompraMenorPrecoVenda(dto.produtos());
        // Pegar código sequencial
        var codigo = sequenceService.getNextValue(Constants.SEQ_ENTRADA_ESTOQUE);
        // Lista de ProdutoEntradaEstoque
        List<ProdutoEntradaEstoque> entradasProdutos = new ArrayList<>(this.mapToProdutoEntradaEstoqueList(dto.produtos(), null, dto.situacao().equals(Situacao.EM_CADASTRAMENTO)));
        // Criando o EntradaEstoque
        EntradaEstoque entradaEstoque = entradaEstoqueMapper.toEntity(dto, codigo, entradasProdutos);
        // Referenciando o entradaEstoque para cada entradaProduto (referenciando pai no filho)
        entradasProdutos.forEach(pee -> pee.setEntradaEstoque(entradaEstoque));
        // Persistindo o entradaEstoque
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Transactional
    public EntradaEstoque updateEntradaEstoque(Long id, MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Validações dos produtos
        ValidationsMovimentacao.validateIfProdutosExists(dto.produtos());
        ValidationsMovimentacao.validateIfPrecoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateIfDescontoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataEntradaEstoque());
        validatePrecoCompraMenorPrecoVenda(dto.produtos());
        // Recupera o entradaEstoque do banco de dados
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));
        // Atualiza os campos primitivos
        entradaEstoque.setDescricao(dto.descricao());
        entradaEstoque.setStatus(dto.status());
        entradaEstoque.setDataEntradaEstoque(dto.dataEntradaEstoque());
        // Remover produtos do estoque para situações de Cadastro finalizado
        if (entradaEstoque.getSituacao().equals(Situacao.CADASTRO_FINALIZADO) && dto.situacao().equals(Situacao.CADASTRO_FINALIZADO)){
            entradaEstoque.getEntradasProdutos().forEach(this::removerQtdProdutosEstoque);
        }
        entradaEstoque.setSituacao(dto.situacao());
        // Limpa os produtos antigos
        entradaEstoque.getEntradasProdutos().clear();
        // Gera novos ProdutoEntradaEstoque com base no DTO
        List<ProdutoEntradaEstoque> novasEntradas = this.mapToProdutoEntradaEstoqueList(dto.produtos(), entradaEstoque, dto.situacao().equals(Situacao.EM_CADASTRAMENTO));
        // Referencia todos os ProdutoEntradaEstoque no entradaEstoque
        entradaEstoque.getEntradasProdutos().addAll(novasEntradas);
        // Persiste as informações
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Transactional
    public void deleteEntradaEstoque(Long id) {
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));
        validateEstoqueOnDelete(entradaEstoque);
        entradaEstoque.getEntradasProdutos().forEach(this::removerQtdProdutosEstoque);
        entradaEstoqueRepository.delete(entradaEstoque);
    }

    private void removerQtdProdutosEstoque(ProdutoEntradaEstoque produtoEntradaEstoque) {
        this.produtoCommandService.removerEstoque(produtoEntradaEstoque.getProduto().getId(), produtoEntradaEstoque.getQuantidade());
    }

    // Método para mapear um array de dtos de ProdutoMovimentacaoEstoque para um array de  ProdutoEntradaEstoque
    private List<ProdutoEntradaEstoque> mapToProdutoEntradaEstoqueList(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtosDTO, EntradaEstoque entradaEstoque, Boolean isEmCadastramento) {
        return produtosDTO.stream().map(dto -> {
            Produto produto = produtoQueryService.findById(Long.parseLong(dto.idProduto()));
            produto.setPrecoCusto(dto.precoUnitario());
            CreateUpdateProdutoDTO produtoDTO = produtoDTOMapper.toDto(produto);
            produtoCommandService.update(produto.getId(), produtoDTO);
            if (!isEmCadastramento) {
                atualizarEstoquePorProduto(dto);
            }
            return produtoEntradaEstoqueMapper.toEntity(dto, produto, entradaEstoque);
        }).collect(Collectors.toList());
    }

    private void atualizarEstoquePorProduto(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDTO) {
        this.produtoCommandService.adicionarEstoque(Long.parseLong(produtoDTO.idProduto()), produtoDTO.quantidade());
    }

    private void validatePrecoCompraMenorPrecoVenda(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        produtos.forEach(p -> {
            var produto = produtoQueryService.findById(Long.parseLong(p.idProduto()));
            if (produto.getPrecoVenda().compareTo(p.precoUnitario()) < 0)
                throw new PrecoCustoMaiorPrecoVendaException(produto.getNome());
        });
    }

    private void validateEstoqueOnDelete(EntradaEstoque entradaEstoque) {
        entradaEstoque.getEntradasProdutos().forEach(p -> {
            Produto produtoEntrada = p.getProduto();
            if (produtoEntrada.getQuantidadeEmEstoque() < p.getQuantidade())
                throw new DelecaoNaoPermitidaQtdEstoqueInsuficienteException(produtoEntrada.getNome(), produtoEntrada.getQuantidadeEmEstoque(), p.getQuantidade());
        });
    }
}
