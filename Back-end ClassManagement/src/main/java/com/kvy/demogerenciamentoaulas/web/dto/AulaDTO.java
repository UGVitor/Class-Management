package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class AulaDTO {

    private Long id;
    private String descricao; // Descrição da aula
    private Long disciplinaId; // ID da disciplina associada
    private Long salaId; // ID da sala associada
    private Long horarioId; // ID do horário associado
    private Long diaSemanaId; // ID dos dias da semana associados

    // Construtor
    public AulaDTO(Long id, String descricao, Long disciplinaId, Long salaId, Long horarioId, Long diaSemanaId) {
        this.id = id;
        this.descricao = descricao;
        this.disciplinaId = disciplinaId;
        this.salaId = salaId;
        this.horarioId = horarioId;
        this.diaSemanaId = diaSemanaId;
    }


  
}



