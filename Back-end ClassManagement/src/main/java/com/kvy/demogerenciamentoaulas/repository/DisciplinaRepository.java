package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByNome(String nome);

    @Query("SELECT d.id AS id, d.nome AS nome, d.descricao AS descricao, " +
            "l.login AS loginNome, " +
            "t.nome AS turmaNome " +
            "FROM Disciplina d " +
            "JOIN d.login l " +
            "JOIN d.turma t")

    List<DisciplinaProjection> findAllDisciplinaWithLoginAndTurma();
}