package io.github.mateuussilvapb.app_jm_perfumaria.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

@Getter
@EqualsAndHashCode
public class Reference<T> implements Referable<T>, Comparable<Reference<T>> {

    private final transient T identificacao;
    private final String descricao;

    public Reference(T id, String descricao) {
        this.identificacao = id;
        this.descricao = descricao;
    }

    public Reference(Referable<T> referable) {
        this(referable.getIdentificacao(), referable.getDescricao());
    }

    public static <T> Reference<T> valueOf(Referable<T> referable) {
        return new Reference<>(referable);
    }

    public static <T> List<Reference<T>> valueOf(Collection<? extends Referable<T>> referables) {
        return referables.stream().map(Reference::valueOf).toList();
    }

    @Override
    public String toString() {
        return "%s - %s".formatted(String.valueOf(identificacao), descricao);
    }

    @Override
    public int compareTo(@NonNull Reference<T> other) {
        return descricao.compareTo(other.descricao);
    }
}