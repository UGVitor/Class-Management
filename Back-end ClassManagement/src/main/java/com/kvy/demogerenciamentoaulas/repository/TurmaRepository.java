package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
    List<TurmaProjection> findAllTurmasWithPeriodoAndTurnoAndCursoAndSemestre();

    @Query("SELECT t FROM Turma t WHERE t.nome = :nome AND t.periodo.id = :periodoId AND t.turno.id = :turnoId AND t.semestre.id = :semestreId AND t.curso.id = :cursoId")
    Optional<Turma> findByUniqueAttributes(
            @Param("nome") String nome,
            @Param("periodoId") Long periodoId,
            @Param("turnoId") Long turnoId,
            @Param("semestreId") Long semestreId,
            @Param("cursoId") Long cursoId
    );
}