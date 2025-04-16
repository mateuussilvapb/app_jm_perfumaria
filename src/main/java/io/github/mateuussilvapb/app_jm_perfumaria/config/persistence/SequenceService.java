package io.github.mateuussilvapb.app_jm_perfumaria.config.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long getNextValue(String sequenceName) {
        String sql = "SELECT nextval(:seqName)";
        return ((Number) entityManager
                .createNativeQuery(sql)
                .setParameter("seqName", sequenceName)
                .getSingleResult()).longValue();
    }
}
