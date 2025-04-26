package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.interfaces.ProdutoToProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoCommandService {

    private final ProdutoToProdutoDTO atualizarProdutoMapper;
    private final ProdutoQueryService produtoQueryService;

    public void deleteById(Long id) {
        var produto = produtoQueryService.findById(id);
        produto.setStatus(Status.INATIVO);
        produto.setSituacao(Situacao.CADASTRO_FINALIZADO);
        this.update(id, atualizarProdutoMapper.toDto(produto));
    }

    public Produto update(Long id, CreateUpdateProdutoDTO updatedProduto) {
        var produto = produtoQueryService.findById(id);
        //TODO: Necessário implementar service de Categoria e Marca para validação de ids
        return null;
    }

    public Produto create(CreateUpdateProdutoDTO createdProduto) {
        //TODO: Necessário implementar service de Categoria e Marca para validação de ids
        return null;
    }

}
