package com.kvy.demogerenciamentoaulas.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import jakarta.persistence.*;
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

    @Column(name = "sala", nullable = false, length = 50)
    private String sala;

    @Column(name = "numero", nullable = false)
    private int numero; // Novo atributo

    @Column(name = "capacidade", nullable = false)
    private int capacidade; // Novo atributo

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tiposala", nullable = false)
    @JsonBackReference("tiposala-sala")
    private TipoSala tipoSala;

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
                ", sala='" + sala + '\'' +
                ", numero=" + numero +
                ", capacidade=" + capacidade +
                '}';
    }
}
