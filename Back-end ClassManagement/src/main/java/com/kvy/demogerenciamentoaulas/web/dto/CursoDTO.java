package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CursoDTO {

    private Long id;

    @NotBlank(message = "O campo curso é obrigatório")
    @Size(max = 100, message = "O campo curso não pode ter mais de 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O campo curso só pode conter letras e espaços")
    private String curso;

    @Setter
    @Getter
    @NotNull(message = "O campo modalidade é obrigatório")
    @Positive(message = "O campo modalidade deve ser um valor positivo")
    private Long modalidade;

}