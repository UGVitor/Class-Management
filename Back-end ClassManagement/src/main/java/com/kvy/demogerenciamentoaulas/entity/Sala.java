package com.kvy.demogerenciamentoaulas.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Sala")
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", nullable = false, length = 2)
    private Integer numero;

    @Column(name = "capacidade", nullable = false, length = 3)
    private Integer capacidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tiposala", nullable = false)
    @JsonBackReference("tiposala-sala")
    private TipoSala tipoSala;

    public static Snippet builder() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala that = (Sala) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", numero=" + numero +
                ", capacidade=" + capacidade +
                '}';
    }
}
