package com.kvy.demogerenciamentoaulas.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
import java.time.LocalTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "aula")
public class Aula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "data", nullable = false)
    private LocalDate data;
    @Column(name = "horario", nullable = false)
    private LocalTime horario;
    @Column(name = "duracao", nullable = false)
    private int duracao;
    @Column(name = "topico", nullable = false, length = 200)
    private String topico;
    @Column(name = "cod_disciplina", nullable = false)
    private Long cod_disciplina;
<<<<<<< HEAD
    @Column(name = "status", nullable = false, length = 25)
    private boolean status = false;

=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

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
<<<<<<< HEAD


    public boolean getStatus() {
        return this.status;
    }
=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
}
