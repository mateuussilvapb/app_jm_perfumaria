package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository.ISaidaEstoqueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaidaEstoqueQueryService {

    private final ISaidaEstoqueRepository saidaEstoqueRepository;

    public List<SaidaEstoque> findAll() {
        return this.saidaEstoqueRepository.findAll();
    }
}
