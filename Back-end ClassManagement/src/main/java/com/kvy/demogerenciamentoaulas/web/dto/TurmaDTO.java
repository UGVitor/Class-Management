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
    private Long periodoId;
    private Long turnoId;
    private Long cursoId;
    private Long semestreId;



}
