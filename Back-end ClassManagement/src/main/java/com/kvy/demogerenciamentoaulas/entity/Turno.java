package com.kvy.demogerenciamentoaulas.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Data
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Turno")
public class Turno implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "turno", nullable = false)
    private String turno;
}

