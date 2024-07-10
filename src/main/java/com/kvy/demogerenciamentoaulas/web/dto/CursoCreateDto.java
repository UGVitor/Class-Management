package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CursoCreateDto {

    @NotBlank(message = "Turma invalida")
    private String turma;
    @NotBlank(message = "Turno invalido")
    private String turno;
}
