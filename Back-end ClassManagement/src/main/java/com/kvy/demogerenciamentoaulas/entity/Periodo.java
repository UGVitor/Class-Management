package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "nome", nullable = false, unique = true)
        private String nome;

        @OneToMany(mappedBy = "periodo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonManagedReference("periodo-turma")
        private Set<Turma> turmas = new HashSet<>();


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Periodo that = (Periodo) o;
                return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
                return Objects.hashCode(id);
        }

        @Override
        public String toString() {
                return "Periodo{" +
                        "id=" + id +
                        '}';
        }
}