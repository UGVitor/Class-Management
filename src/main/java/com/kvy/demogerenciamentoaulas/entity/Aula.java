package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
    @Column(name = "horario", nullable = false)
    private LocalTime horario;
    @Column(name = "duracao", nullable = false)
    private int duracao;
    @Column(name = "topico", nullable = false, length = 200)
    private String topico;
    @Column(name = "cod_disciplina", nullable = false)
    private Long cod_disciplina;
    @Column(name = "status", length = 25)
    private boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = false)
    private Role dia;

    public enum Role{
        SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id")
    @JsonBackReference("disciplina-referencia")
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



    public boolean getStatus() {
        return this.status;
    }

}
