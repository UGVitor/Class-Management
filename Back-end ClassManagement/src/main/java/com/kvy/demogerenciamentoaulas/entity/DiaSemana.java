package com.kvy.demogerenciamentoaulas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "DiaSemana")
public class DiaSemana implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dia", nullable = false)
    private String dia;

}
