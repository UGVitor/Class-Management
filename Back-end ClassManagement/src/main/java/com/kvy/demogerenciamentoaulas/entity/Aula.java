package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

@Builder
@Entity
@Data
@AllArgsConstructor
@Table(name = "aula")
public class Aula implements Serializable {

    public Aula() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina")
    @JsonBackReference("disciplina-aula")
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_horario", nullable = false)
    @JsonBackReference("horario-aula")
    private Horario horario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aula", nullable = false)
    @JsonBackReference("sala-aula")
    private Sala sala;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dia_semana", nullable = false)
    @JsonBackReference("diaSemana-aula")
    private DiaSemana diaSemana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Turma", nullable = false)
    @JsonBackReference("Turma-aula")
    private Turma turma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aula aula = (Aula) o;
        return Objects.equals(id, aula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                '}';
    }

    public int getSalaNumero() { return sala != null ? sala.getNumero() : null; }
    public LocalTime getHorarioInicio() {return horario != null ? horario.getHoraInicio() : null;}
    public LocalTime getHorarioTermino() {return horario != null ? horario.getHoraTermino() : null;}
    public String getDiaSemanaNome() {return diaSemana != null ? diaSemana.getDia() : null;}
    public String getTipoSalaNome(){return sala != null ? sala.getTipoSala().getTipoSala() : null;}
    public String getDisciplinaNome() { return disciplina != null ? disciplina.getNome() : null; }
    public String getLoginNome() { return disciplina != null ? disciplina.getLogin().getLogin() : null; }
    public String getTurmaNome() { return turma != null ? turma.getNome() : null; }
    public String getTurnoNome() { return turma != null ? turma.getTurno().getTurno() : null; }
    public Long getDisciplinaId() { return disciplina != null ? disciplina.getId() : null; }



    public String getStatus() {
        LocalTime agora = LocalTime.now();
        LocalTime inicio = getHorarioInicio();
        LocalTime termino = getHorarioTermino();

        if (inicio == null || termino == null) {
            return "Horário não definido";
        }

        if (agora.isBefore(inicio)) {
            return "Não Iniciada";
        } else if (agora.isAfter(inicio) && agora.isBefore(termino)) {
            return "Em Andamento";
        } else {
            return "Finalizada";
        }
    }
}