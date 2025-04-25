package io.github.mateuussilvapb.app_jm_perfumaria.infra.common.service;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.util.List;
import java.util.Optional;

public interface IReadOnlyService<T> {
    Optional<T> findById(Long id);

    List<T> findAllAtivos();

    List<T> findAllInativos();

    List<T> findAllByTermAndStatus(String searchTerm, Status status);
}
