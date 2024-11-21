package com.kvy.demogerenciamentoaulas.web.dto;

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
    private String nome;
    private Long periodo;
    private Long turno;
    private Long curso;
    private Long semestre;



}
