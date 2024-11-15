package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.Data;

@Data
public class TurmaDTO {
    private Long id;
    private String nomeTurma;
    private String nomePeriodo;
    private String nomeSemestre;
    private String nomeTurno;
    private String nomeCurso;

    // Construtor
    public TurmaDTO(Long id, String nomeTurma, String nomePeriodo, String nomeSemestre, String nomeTurno, String nomeCurso) {
        this.id = id;
        this.nomeTurma = nomeTurma;
        this.nomePeriodo = nomePeriodo;
        this.nomeSemestre = nomeSemestre;
        this.nomeTurno = nomeTurno;
        this.nomeCurso = nomeCurso;
    }


}
