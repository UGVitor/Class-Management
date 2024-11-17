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
    private Long diasDaSemanaId; // ID dos dias da semana associados

    // Construtor
    public AulaDTO(Long id, String descricao, Long disciplinaId, Long salaId, Long horarioId, Long diasDaSemanaId) {
        this.id = id;
        this.descricao = descricao;
        this.disciplinaId = disciplinaId;
        this.salaId = salaId;
        this.horarioId = horarioId;
        this.diasDaSemanaId = diasDaSemanaId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Long getSalaId() {
        return salaId;
    }

    public void setSalaId(Long salaId) {
        this.salaId = salaId;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }

    public Long getDiasDaSemanaId() {
        return diasDaSemanaId;
    }

    public void setDiasDaSemanaId(Long diasDaSemanaId) {
        this.diasDaSemanaId = diasDaSemanaId;
    }
}



