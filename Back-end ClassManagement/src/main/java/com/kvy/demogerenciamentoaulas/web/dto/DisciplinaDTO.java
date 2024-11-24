package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplinaDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório.")
    @Size(max = 100, message = "O campo nome não pode ter mais de 100 caracteres.")
    private String nome;

    @NotBlank(message = "O campo descrição é obrigatório.")
    @Size(max = 255, message = "O campo descrição não pode ter mais de 255 caracteres.")
    private String descricao;

    @NotNull(message = "O campo loginId é obrigatório.")
    private Long loginId;

    @NotNull(message = "O campo turmaId é obrigatório.")
    private Long turmaId;
}
