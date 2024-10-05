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
@Table(name = "semestre")
public class Semestre implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "semestre", nullable = false)
        private String semestre;

        @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JsonManagedReference("semestre-turma")
        private Set<Turma> turmas = new HashSet<>();


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Semestre that = (Semestre) o;
                return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
                return Objects.hashCode(id);
        }

        @Override
        public String toString() {
                return "Semestre{" +
                        "id=" + id +
                        '}';
        }
}