package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AulaCreateDto {

    private LocalDate data;

    private LocalTime horario;

    private int duracao;

    private String topico;

    private Long cod_disciplina;
}
