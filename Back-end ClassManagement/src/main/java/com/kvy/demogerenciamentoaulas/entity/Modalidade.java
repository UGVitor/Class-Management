package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "Modalidade")
public class Modalidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "modalidade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("modalidade-curso")
    private Set<Curso> cursos = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modalidade that = (Modalidade) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Modalidade{" +
                "id=" + id +
                '}';
    }

    public Modalidade(String nome) {
        this.nome = nome;
    }

}