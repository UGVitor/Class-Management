package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "Curso")
public class Curso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Curso", nullable = false, length = 50)
    private String Curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modalidade", nullable = false)
    @JsonBackReference("modalidade-curso")
    private Modalidade modalidade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso that = (Curso) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                '}';
    }
    public String getModalidadeNome() {
        return modalidade != null ? modalidade.getNome() : null;
    }
}
