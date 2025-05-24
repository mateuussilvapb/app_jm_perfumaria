package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.ProdutoMovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueSemProdutosException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueDTOtoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command.ProdutoCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.mapper.IProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueDTOtoProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.config.persistence.SequenceService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository.IEntradaEstoqueRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.Constants;
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

    public EntradaEstoque createEntradaEstoqueComProdutos(MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Valida se o dto possui produtos
        this.validarProdutos(dto.produtos());
        // Pegar código sequencial
        var codigo = sequenceService.getNextValue(Constants.SEQ_ENTRADA_ESTOQUE);
        // Lista de ProdutoEntradaEstoque
        List<ProdutoEntradaEstoque> entradasProdutos = new ArrayList<>(this.mapToProdutoEntradaEstoqueList(dto.produtos(), null));
        // Criando o EntradaEstoque
        EntradaEstoque entradaEstoque = entradaEstoqueMapper.toEntity(dto, codigo, entradasProdutos);
        // Referenciando o entradaEstoque para cada entradaProduto (referenciando pai no filho)
        entradasProdutos.forEach(pee -> pee.setEntradaEstoque(entradaEstoque));
        // Persistindo o entradaEstoque
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Transactional
    public EntradaEstoque updateEntradaEstoque(Long id, MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Valida se o dto possui produtos
        this.validarProdutos(dto.produtos());
        // Recupera o entradaEstoque do banco de dados
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));
        // Atualiza os campos primitivos
        entradaEstoque.setDescricao(dto.descricao());
        entradaEstoque.setStatus(dto.status());
        entradaEstoque.setSituacao(dto.situacao());
        // Limpa os produtos antigos
        entradaEstoque.getEntradasProdutos().clear();
        // Gera novos ProdutoEntradaEstoque com base no DTO
        List<ProdutoEntradaEstoque> novasEntradas = this.mapToProdutoEntradaEstoqueList(dto.produtos(), entradaEstoque);
        // Referencia todos os ProdutoEntradaEstoque no entradaEstoque
        entradaEstoque.getEntradasProdutos().addAll(novasEntradas);
        // Persiste as informações
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    public void deleteEntradaEstoque(Long id) {
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));
        entradaEstoqueRepository.delete(entradaEstoque);
    }

    // Método para mapear um array de dtos de ProdutoEntradaEstoque para um array de ProdutoEntradaEstoque
    private List<ProdutoEntradaEstoque> mapToProdutoEntradaEstoqueList(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtosDTO, EntradaEstoque entradaEstoque) {
        return produtosDTO.stream().map(dto -> {
            Produto produto = produtoQueryService.findById(Long.parseLong(dto.idProduto()));
            produto.setPrecoCusto(dto.precoUnitario());
            CreateUpdateProdutoDTO produtoDTO = produtoDTOMapper.toDto(produto);
            produtoCommandService.update(produto.getId(), produtoDTO);
            return produtoEntradaEstoqueMapper.toEntity(dto, produto, entradaEstoque);
        }).collect(Collectors.toList());
    }

    // Validando se o dto possui produtos
    private void validarProdutos(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new EntradaEstoqueSemProdutosException();
        }
    }

}
