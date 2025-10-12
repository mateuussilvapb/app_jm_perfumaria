package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.validacoes.ValidationsMovimentacao;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.exceptions.SaidaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueDTOtoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command.ProdutoCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueDTOtoProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository.ISaidaEstoqueRepository;
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
public class SaidaEstoqueCommandService {

    private final ISaidaEstoqueRepository saidaEstoqueRepository;
    private final ProdutoQueryService produtoQueryService;
    private final SequenceService sequenceService;
    private final ISaidaEstoqueDTOtoSaidaEstoque saidaEstoqueMapper;
    private final IProdutoSaidaEstoqueDTOtoProdutoSaidaEstoque produtoSaidaEstoqueMapper;
    private final IProdutoToProdutoDTO produtoDTOMapper;
    private final ProdutoCommandService produtoCommandService;

    @Transactional
    public SaidaEstoque createSaidaEstoqueComProdutos(MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Validações dos produtos
        ValidationsMovimentacao.validateIfProdutosExists(dto.produtos());
        ValidationsMovimentacao.validateIfPrecoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateIfDescontoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataMovimentacaoEstoque());
        // Pegar código sequencial
        var codigo = sequenceService.getNextValue(Constants.SEQ_SAIDA_ESTOQUE);
        // Lista de ProdutoSaidaEstoque
        List<ProdutoSaidaEstoque> saidasProdutos = new ArrayList<>(this.mapToProdutoSaidaEstoqueList(dto.produtos(), null, dto.situacao().equals(Situacao.EM_CADASTRAMENTO)));
        // Criando o SaidaEstoque
        SaidaEstoque saidaEstoque = saidaEstoqueMapper.toEntity(dto, codigo, saidasProdutos);
        // Referenciando o saidaEstoque para cada saidaProduto (referenciando pai no filho)
        saidasProdutos.forEach(pee -> pee.setSaidaEstoque(saidaEstoque));
        // Persistindo o saidaEstoque
        return saidaEstoqueRepository.save(saidaEstoque);
    }

    @Transactional
    public SaidaEstoque updateSaidaEstoque(Long id, MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Validações dos produtos
        ValidationsMovimentacao.validateIfProdutosExists(dto.produtos());
        ValidationsMovimentacao.validateIfPrecoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateIfDescontoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataMovimentacaoEstoque());
        // Recupera o saidaEstoque do banco de dados
        SaidaEstoque saidaEstoque = saidaEstoqueRepository.findById(id).orElseThrow(() -> new SaidaEstoqueNotFoundException(id));
        // Atualiza os campos primitivos
        saidaEstoque.setDescricao(dto.descricao());
        saidaEstoque.setStatus(dto.status());
        saidaEstoque.setDataSaidaEstoque(dto.dataMovimentacaoEstoque());
        // Adicionar produtos do estoque para situações de Cadastro finalizado
        if (saidaEstoque.getSituacao().equals(Situacao.CADASTRO_FINALIZADO) && dto.situacao().equals(Situacao.CADASTRO_FINALIZADO)){
            saidaEstoque.getSaidasProdutos().forEach(this::adicionarQtdProdutosEstoque);
        }
        saidaEstoque.setSituacao(dto.situacao());
        // Limpa os produtos antigos
        saidaEstoque.getSaidasProdutos().clear();
        // Gera novos ProdutoSaidaEstoque com base no DTO
        List<ProdutoSaidaEstoque> novasSaidas = this.mapToProdutoSaidaEstoqueList(dto.produtos(), saidaEstoque, dto.situacao().equals(Situacao.EM_CADASTRAMENTO));
        // Referencia todos os ProdutoSaidaEstoque no saidaEstoque
        saidaEstoque.getSaidasProdutos().addAll(novasSaidas);
        // Persiste as informações
        return saidaEstoqueRepository.save(saidaEstoque);
    }

    @Transactional
    public void deleteSaidaEstoque(Long id) {
        SaidaEstoque saidaEstoque = saidaEstoqueRepository.findById(id).orElseThrow(() -> new SaidaEstoqueNotFoundException(id));
        saidaEstoque.getSaidasProdutos().forEach(this::adicionarQtdProdutosEstoque);
        saidaEstoqueRepository.delete(saidaEstoque);
    }

    private void adicionarQtdProdutosEstoque(ProdutoSaidaEstoque produtoSaidaEstoque) {
        this.produtoCommandService.adicionarEstoque(produtoSaidaEstoque.getProduto().getId(), produtoSaidaEstoque.getQuantidade());
    }

    // Método para mapear um array de dtos de ProdutoMovimentacaoEstoque para um array de  ProdutoSaidaEstoque
    private List<ProdutoSaidaEstoque> mapToProdutoSaidaEstoqueList(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtosDTO, SaidaEstoque saidaEstoque, Boolean isEmCadastramento) {
        return produtosDTO.stream().map(dto -> {
            Produto produto = produtoQueryService.findById(Long.parseLong(dto.idProduto()));
            produto.setPrecoVenda(dto.precoUnitario());
            CreateUpdateProdutoDTO produtoDTO = produtoDTOMapper.toDto(produto);
            produtoCommandService.update(produto.getId(), produtoDTO);
            if (!isEmCadastramento) {
                atualizarEstoquePorProduto(dto);
            }
            return produtoSaidaEstoqueMapper.toEntity(dto, produto, saidaEstoque);
        }).collect(Collectors.toList());
    }

    private void atualizarEstoquePorProduto(ProdutoMovimentacaoEstoqueCreateUpdateDTO produtoDTO) {
        this.produtoCommandService.removerEstoque(Long.parseLong(produtoDTO.idProduto()), produtoDTO.quantidade(), false);
    }
}
