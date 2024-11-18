package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Data
@Table(name = "aula")
public class Aula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;

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

    public String getSala() {
        return sala != null ? sala.getSala() : null;
    }
    public String getDisciplina() {
        return disciplina != null ? disciplina.getNome() : null;
    }
    public LocalTime getHorarioIncicio() {return horario != null ? horario.getHoraInicio() : null;}
    public LocalTime getHorarioTermino() {return horario != null ? horario.getHoraTermino() : null;}
    public String getDiaSemana() {return diaSemana != null ? diaSemana.getDia() : null;}

}
