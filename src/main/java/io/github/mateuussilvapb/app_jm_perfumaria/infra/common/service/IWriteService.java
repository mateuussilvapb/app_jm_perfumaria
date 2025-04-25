package io.github.mateuussilvapb.app_jm_perfumaria.infra.common.service;

public interface IWriteService<T, C, U> {
    T create(Long id, C data);

    T update(Long id, U data);

    void deleteById(Long id);
}