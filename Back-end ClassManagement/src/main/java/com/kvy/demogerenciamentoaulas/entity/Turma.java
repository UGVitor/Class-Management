package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Data
@Setter
@NoArgsConstructor
@Entity
@Table(name = "turma")
public class Turma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 20)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_periodo", nullable = false)
    @JsonBackReference("periodo-turma")
    private Periodo periodo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turno", nullable = false)
    @JsonBackReference("turno-turma")
    private Turno turno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonBackReference("curso-turma")
    private Curso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_semestre", nullable = false)
    @JsonBackReference("semestre-turma")
    private Semestre semestre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turma turma = (Turma) o;
        return Objects.equals(id, turma.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Turma{" +
                "id=" + id +
                '}';
    }

    private String getPeriodoNome(){
        return periodo != null ? periodo.getNome() : null;
    }

    private String getTurnoNome(){
        return turno != null ? turno.getTurno() : null;
    }

    private String getCursoNome(){
        return curso != null ? curso.getCurso() : null;
    }
    private String getSemestreNome(){
        return semestre != null ? semestre.getSemestre() : null;
    }
}
