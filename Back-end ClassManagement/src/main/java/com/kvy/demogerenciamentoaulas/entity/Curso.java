package com.kvy.demogerenciamentoaulas.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter @Data
@NoArgsConstructor
@Entity
@Table(name = "Curso")
public class Curso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "Turma", nullable = false, length = 50)
    private String turma;
    @Column(name = "turno", nullable = false, length = 25)
    private Role turno = Role.MATUTINO;

    public enum Role{
        MATUTINO, DIURNO, NOTURNO
    }

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Disciplina> disciplinas = new HashSet<>();

}
