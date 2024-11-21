package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT t.id AS id, t.nome AS nome, " +
            "p.nome AS periodoNome, " +
            "tu.turno AS turnoNome, " +
            "c.Curso AS cursoNome, " +
            "s.semestre AS semestreNome " +
            "FROM Turma t " +
            "JOIN t.periodo p " +
            "JOIN t.turno tu " +
            "JOIN t.curso c " +
            "JOIN t.semestre s")
        List<TurmaProjection> findAllTurmas();
}
