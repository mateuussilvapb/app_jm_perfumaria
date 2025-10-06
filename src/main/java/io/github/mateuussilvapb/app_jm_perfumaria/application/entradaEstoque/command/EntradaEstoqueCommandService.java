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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataMovimentacaoEstoque());
        validatePrecoCompraMenorPrecoVenda(dto.produtos());

        // Pegar código sequencial
        var codigo = sequenceService.getNextValue(Constants.SEQ_ENTRADA_ESTOQUE);

        if (dto.situacao().equals(Situacao.CADASTRO_FINALIZADO)) {
            dto.produtos().forEach(this::atualizarEstoquePorProduto);
        }

        // Lista de ProdutoEntradaEstoque → aqui atualiza o estoque normalmente
        List<ProdutoEntradaEstoque> entradasProdutos = mapToProdutoEntradaEstoqueList(dto.produtos(), null);

        // Criando a entradaEstoque
        EntradaEstoque entradaEstoque = entradaEstoqueMapper.toEntity(dto, codigo, entradasProdutos);

        // Referenciando o entradaEstoque para cada entradaProduto
        entradasProdutos.forEach(pee -> pee.setEntradaEstoque(entradaEstoque));

        // Persistindo
        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Transactional
    public EntradaEstoque updateEntradaEstoque(Long id, MovimentacaoEstoqueCreateUpdateDTO dto) {
        // Validações dos produtos
        ValidationsMovimentacao.validateIfProdutosExists(dto.produtos());
        ValidationsMovimentacao.validateIfPrecoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateIfDescontoLessThenOne(dto.produtos());
        ValidationsMovimentacao.validateDateIsInThePast(dto.dataMovimentacaoEstoque());
        validatePrecoCompraMenorPrecoVenda(dto.produtos());

        // Recupera entradaEstoque do banco
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));

        // Atualiza campos primitivos
        entradaEstoque.setDescricao(dto.descricao());
        entradaEstoque.setStatus(dto.status());
        entradaEstoque.setDataEntradaEstoque(dto.dataMovimentacaoEstoque());

        // Ajusta estoque apenas se já estava finalizado
        if (entradaEstoque.getSituacao().equals(Situacao.CADASTRO_FINALIZADO) && dto.situacao().equals(Situacao.CADASTRO_FINALIZADO)) {
            tratarProdutosUpdateEntradaEstoque(entradaEstoque, dto);
        }

        entradaEstoque.setSituacao(dto.situacao());

        // Atualiza a lista de produtos sem alterar o estoque
        entradaEstoque.getEntradasProdutos().clear();
        List<ProdutoEntradaEstoque> novasEntradas = mapToProdutoEntradaEstoqueList(dto.produtos(), entradaEstoque);
        entradaEstoque.getEntradasProdutos().addAll(novasEntradas);

        return entradaEstoqueRepository.save(entradaEstoque);
    }

    @Transactional
    public void deleteEntradaEstoque(Long id) {
        EntradaEstoque entradaEstoque = entradaEstoqueRepository.findById(id).orElseThrow(() -> new EntradaEstoqueNotFoundException(id));
        if (entradaEstoque.getSituacao().equals(Situacao.CADASTRO_FINALIZADO)) {
            validateEstoqueOnDelete(entradaEstoque);
            entradaEstoque.getEntradasProdutos().forEach(this::removerQtdProdutosEstoque);
        }
        entradaEstoqueRepository.delete(entradaEstoque);
    }

    private void removerQtdProdutosEstoque(ProdutoEntradaEstoque produtoEntradaEstoque) {
        this.produtoCommandService.removerEstoque(produtoEntradaEstoque.getProduto().getId(), produtoEntradaEstoque.getQuantidade());
    }

    // Método para mapear um array de dtos de ProdutoMovimentacaoEstoque para um array de  ProdutoEntradaEstoque
    private List<ProdutoEntradaEstoque> mapToProdutoEntradaEstoqueList(List<ProdutoMovimentacaoEstoqueCreateUpdateDTO> produtosDTO, EntradaEstoque entradaEstoque) {
        return produtosDTO.stream().map(dto -> {
            Produto produto = produtoQueryService.findById(Long.parseLong(dto.idProduto()));
            produto.setPrecoCusto(dto.precoUnitario());
            CreateUpdateProdutoDTO produtoDTO = produtoDTOMapper.toDto(produto);
            produtoCommandService.update(produto.getId(), produtoDTO);
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

    private void tratarProdutosUpdateEntradaEstoque(EntradaEstoque entradaEstoque, MovimentacaoEstoqueCreateUpdateDTO dto) {

        // Map do DTO (produtos novos) por ID
        Map<Long, ProdutoMovimentacaoEstoqueCreateUpdateDTO> dtoMap = dto.produtos().stream()
                .collect(Collectors.toMap(p -> Long.parseLong(p.idProduto()), Function.identity()));

        // Map dos produtos atuais da entrada por ID
        Map<Long, ProdutoEntradaEstoque> atualMap = entradaEstoque.getEntradasProdutos().stream()
                .collect(Collectors.toMap(pee -> pee.getProduto().getId(), Function.identity()));

        // Ajustar produtos existentes e remover os que não estão mais no DTO
        for (Iterator<Map.Entry<Long, ProdutoEntradaEstoque>> it = atualMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<Long, ProdutoEntradaEstoque> entry = it.next();
            Long produtoId = entry.getKey();
            ProdutoEntradaEstoque ee = entry.getValue();

            ProdutoMovimentacaoEstoqueCreateUpdateDTO eeDto = dtoMap.get(produtoId);

            if (eeDto != null) {
                // Produto existe nos dois → ajustar quantidade se diferente
                ajustarEstoqueNaAlteracao(ee, eeDto);
                dtoMap.remove(produtoId); // remove do map para sobrar apenas produtos novos
            } else {
                // Produto não existe mais no DTO → remover do estoque
                removerQtdProdutosEstoque(ee);
                it.remove(); // remove do map de produtos atuais
                entradaEstoque.getEntradasProdutos().remove(ee); // remove do objeto da entrada
            }
        }

        // Adicionar produtos novos (que estão no DTO, mas não estavam na entrada original)
        for (ProdutoMovimentacaoEstoqueCreateUpdateDTO novoProduto : dtoMap.values()) {
            Produto produto = produtoQueryService.findById(Long.parseLong(novoProduto.idProduto()));
            ProdutoEntradaEstoque novoEE = produtoEntradaEstoqueMapper.toEntity(novoProduto, produto, entradaEstoque);

            // Adiciona no estoque
            atualizarEstoquePorProduto(novoProduto);

            // Adiciona no map e na lista da entrada
            entradaEstoque.getEntradasProdutos().add(novoEE);
        }
    }


    private void ajustarEstoqueNaAlteracao(ProdutoEntradaEstoque ee, ProdutoMovimentacaoEstoqueCreateUpdateDTO eeDto) {
        if (!ee.getProduto().getId().toString().equals(eeDto.idProduto())) {
            return; // Produto diferente, não faz nada
        }

        int quantidadeAtual = ee.getQuantidade();
        int quantidadeNova = eeDto.quantidade();

        if (quantidadeAtual == quantidadeNova) {
            return; // Não há alteração, nada a fazer
        }

        int diferenca = quantidadeNova - quantidadeAtual;

        if (diferenca > 0) {
            // Aumentar estoque
            produtoCommandService.adicionarEstoque(ee.getProduto().getId(), diferenca);
        } else {
            // Reduzir estoque
            this.produtoCommandService.removerEstoque(ee.getProduto().getId(), -diferenca);
        }

        // Atualiza a quantidade no objeto da entrada de estoque
        ee.setQuantidade(quantidadeNova);
    }
}
