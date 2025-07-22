package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.CreateUpdateDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaInUseException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.repository.IMarcaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MarcaCommandService {

    private final MarcaQueryService marcaQueryService;
    private final ProdutoQueryService produtoQueryService;
    private final IMarcaRepository marcaRepository;

    public void deleteById(Long id) {
        var marca = validarMarcaParaRemocaoOuDesabilitacao(id);
        this.marcaRepository.deleteById(marca.getId());
    }

    public Marca update(Long id, CreateUpdateDto updated) {
        var marca = marcaQueryService.findById(id);
        marca.setNome(updated.nome());
        marca.setDescricao(updated.descricao());
        marca.setStatus(updated.status());
        return marcaRepository.save(marca);
    }

    public Marca create(CreateUpdateDto created) {
        var marcaSameName = marcaRepository.findByNome(created.nome());
        if (marcaSameName.isPresent() && marcaSameName.get().getStatus() == Status.INATIVO) {
            var updatedMarca = new CreateUpdateDto(created.nome(),
                    created.descricao(), Status.ATIVO);
            return this.update(marcaSameName.get().getId(), updatedMarca);
        }
        if (marcaSameName.isPresent() && marcaSameName.get().getStatus() == Status.ATIVO) {
            throw new MarcaSameNameException(marcaSameName.get().getNome());
        }
        var marca = new Marca(created.nome(), created.descricao(),
                Status.ATIVO, new ArrayList<>());
        return marcaRepository.save(marca);
    }

    public Marca toogleStatus(Long id) {
        var marca = marcaQueryService.findById(id);
        Status statusAtual = marca.getStatus();
        if (statusAtual.equals(Status.ATIVO)) {
            this.validarMarcaParaRemocaoOuDesabilitacao(id);
            marca.setStatus(Status.INATIVO);
        } else {
            marca.setStatus(Status.ATIVO);
        }

        return marcaRepository.save(marca);
    }

    private Marca validarMarcaParaRemocaoOuDesabilitacao(Long id) {
        var marca = marcaQueryService.findById(id);
        var existsProdutos = produtoQueryService.findByMarca(marca);
        if (!existsProdutos.isEmpty()) {
            var nomesProdutos = existsProdutos.stream().map(Produto::getNome).toList();
            throw new MarcaInUseException(marca.getNome(), nomesProdutos);
        }
        return marca;
    }
}
