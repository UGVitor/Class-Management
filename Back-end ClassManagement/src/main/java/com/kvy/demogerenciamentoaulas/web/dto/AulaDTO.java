package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "ID da disciplina não pode ser nulo.")
    private Long disciplinaId;

    @NotNull(message = "ID do horário não pode ser nulo.")
    private Long horarioId;

    @NotNull(message = "ID da sala não pode ser nulo.")
    private Long salaId;

    @NotNull(message = "ID do dia da semana não pode ser nulo.")
    private Long diaSemanaId;

    @NotNull(message = "ID do dia da semana não pode ser nulo.")
    private Long turmaId;
}
