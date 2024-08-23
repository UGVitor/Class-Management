package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DisciplinaResponseDto {

    private Long id;
    private String nome;
    private String descricao;
    private int cargaHoraria;
    private Long cod_professor;
    private Long cod_curso;
}
