package com.kvy.demogerenciamentoaulas.Adapter;
import com.kvy.demogerenciamentoaulas.entity.*;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;

public class AulaAdapter {


    public static Aula toEntity(AulaDTO dto, Disciplina disciplina, Horario horario, Sala sala, DiaSemana diaSemana, Turma turma) {
        return Aula.builder()
                .disciplina(disciplina)
                .horario(horario)
                .sala(sala)
                .diaSemana(diaSemana)
                .turma(turma)
                .build();
    }

    public static AulaDTO toDTO(Aula aula) {
        return AulaDTO.builder()
                .id(aula.getId())
                .disciplinaId(aula.getDisciplina().getId())
                .horarioId(aula.getHorario().getId())
                .salaId(aula.getSala().getId())
                .diaSemanaId(aula.getDiaSemana().getId())
                .turmaId(aula.getTurma().getId())
                .build();
    }
}
