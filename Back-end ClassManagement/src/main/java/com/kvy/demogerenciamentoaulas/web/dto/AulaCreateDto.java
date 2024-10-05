package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AulaCreateDto {

    @NotBlank(message = "Data invalida")
    private LocalDate data;
    @NotBlank(message = "Hora invalida")
    private LocalTime horario;
    @NotBlank(message = "Duração invalida")
    private int duracao;
    @NotBlank(message = "Topico invalida")
    private String topico;
    @NotBlank(message = "Codigo invalida")
    private Long cod_disciplina;
}
