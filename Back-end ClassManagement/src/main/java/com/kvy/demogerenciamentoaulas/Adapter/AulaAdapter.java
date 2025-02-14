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

        public static Aula toEntity(AulaDTO aulaDTO) {
            if (aulaDTO == null) {
                return null;
            }
            Aula aula = new Aula();
            aula.setId(aulaDTO.getId());

            Disciplina disciplina = new Disciplina();
            disciplina.setId(aulaDTO.getDisciplinaId());
            aula.setDisciplina(disciplina);

            Horario horario = new Horario();
            horario.setId(aulaDTO.getHorarioId());
            aula.setHorario(horario);

            Sala sala = new Sala();
            sala.setId(aulaDTO.getSalaId());
            aula.setSala(sala);

            Turma turma = new Turma();
            turma.setId(aulaDTO.getTurmaId());
            aula.setTurma(turma);

            DiaSemana diaSemana = new DiaSemana();
            diaSemana.setId(aulaDTO.getDiaSemanaId());
            aula.setDiaSemana(diaSemana);

            return aula;
        }
    }

