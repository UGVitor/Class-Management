package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "disciplina")
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @Column(name = "cargaHoraria", nullable = false)
    private int cargaHoraria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Login professor;

    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("disciplina-aula")
    private Set<Aula> aulas = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                '}';
    }
}
