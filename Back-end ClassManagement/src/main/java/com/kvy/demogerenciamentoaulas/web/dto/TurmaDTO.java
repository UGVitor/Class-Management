package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O campo nome só pode conter letras e espaços")
    private String nome;

    @NotNull(message = "O campo periodo é obrigatório")
    @Positive(message = "O campo periodo deve ser um valor positivo")
    private Long periodo;

    @NotNull(message = "O campo turno é obrigatório")
    @Positive(message = "O campo turno deve ser um valor positivo")
    private Long turno;

    @NotNull(message = "O campo curso é obrigatório")
    @Positive(message = "O campo curso deve ser um valor positivo")
    private Long curso;

    @NotNull(message = "O campo semestre é obrigatório")
    @Positive(message = "O campo semestre deve ser um valor positivo")
    private Long semestre;

}
