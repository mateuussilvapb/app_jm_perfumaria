package io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUpdateDto(@NotBlank @Size(min = 3, max = 100) String nome,
                              @NotBlank @Size(max = 1000) String descricao,
                              @NotNull(message = "O status é obrigatório") Status status) {
}
