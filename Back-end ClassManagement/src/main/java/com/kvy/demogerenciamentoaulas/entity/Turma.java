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

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    @JsonBackReference("periodo-turma")
    private Periodo periodo;

    @ManyToOne
    @JoinColumn(name = "id_turno", nullable = false)
    @JsonBackReference("turno-turma")
    private Turno turno;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    @JsonBackReference("curso-turma")
    private Curso curso;

    @ManyToOne
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


}
