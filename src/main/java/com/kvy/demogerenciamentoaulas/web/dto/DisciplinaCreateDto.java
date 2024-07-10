package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DisciplinaCreateDto {

    private String nome;

    private String descricao;

    private int cargaHoraria;

    private Long cod_profesor;
}
