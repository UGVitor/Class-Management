package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TipoSala")
public class TipoSala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipoSala", nullable = false, length = 50)
    private String tipoSala;

    @OneToMany(mappedBy = "tipoSala", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("tiposala-sala")
    private Set<Sala> salas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoSala that = (TipoSala) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TipoSala{" +
                "id=" + id +
                '}';
    }
}
