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
public class AulaDTO {

    private Long id;

    @NotBlank(message = "Descrição não pode ser em branco.")
    @Size(max = 255, message = "Descrição não pode ter mais que 255 caracteres.")
    private String descricao;

    @NotNull(message = "ID da disciplina não pode ser nulo.")
    private Long disciplinaId;

    @NotNull(message = "ID do horário não pode ser nulo.")
    private Long horarioId;

    @NotNull(message = "ID da sala não pode ser nulo.")
    private Long salaId;

    @NotNull(message = "ID do dia da semana não pode ser nulo.")
    private Long diaSemanaId;
}
