package io.github.mateuussilvapb.app_jm_perfumaria.config.persistence;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.tsid.TsidGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@MappedSuperclass
public class DomainEntity implements Serializable {

    @Id
    private final Long id;

    public DomainEntity() {
        this.id = TsidGenerator.generateLong();
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        DomainEntity that = (DomainEntity) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
