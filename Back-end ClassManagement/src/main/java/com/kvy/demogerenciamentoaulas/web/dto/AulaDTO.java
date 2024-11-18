package com.kvy.demogerenciamentoaulas.web.dto;


import lombok.Data;
import java.io.Serializable;

@Data
public class AulaDTO implements Serializable {
    private Long id;
    private String descricao;
    private Long disciplinaId;
    private Long horarioId;
    private Long salaId;
    private Long diaSemanaId;

    public AulaDTO(Long id, String descricao, Long disciplinaId, Long horarioId, Long salaId, Long diaSemanaId) {
        this.id = id;
        this.descricao = descricao;
        this.disciplinaId = disciplinaId;
        this.horarioId = horarioId;
        this.salaId = salaId;
        this.diaSemanaId = diaSemanaId;
    }
}
