package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT c.id AS id, c.Curso AS curso, m.nome AS modalidadeNome " +
            "FROM Curso c JOIN c.modalidade m")
    List<CursoProjection> findAllCursosWithModalidadeNome();
}
