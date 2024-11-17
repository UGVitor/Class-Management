package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Dias da semana")
public class DiasDaSemana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dias", nullable = false)
    private String nome;


    @OneToMany(mappedBy = "diasDaSemana", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("diasDaSemana-aula")
    private Set<Aula> aulas = new HashSet<>();
     // Confirme o relacionamento inverso, se necessário

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiasDaSemana dias = (DiasDaSemana) o;
        return Objects.equals(id, dias.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dias da Semana{" +
                "id=" + id +
                '}';
    }
    }


