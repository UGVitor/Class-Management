package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.web.dto.Filtro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    /*@Query("SELECT new com.kvy.demogerenciamentoaulas.web.dto.Filtro(a.horario, a.duracao, d.nome, p.login, c.periodo) " +
            "FROM Aula a " +
            "JOIN a.disciplina d " +
            "JOIN d.professor p " +
            "JOIN d.curso c " +
            "WHERE (:data IS NULL OR a.data = :data) " +
            "AND (:turno IS NULL OR c.turno = :turno) " +
            "AND (:curso IS NULL OR c.nome = :curso) " +
            "AND (:periodo IS NULL OR c.periodo = :periodo)")
    List<Filtro> filtrarAulas(@Param("data") LocalDate data,
                              @Param("turno") String turno,
                              @Param("curso") String curso,
                              @Param("periodo") String periodo);*/

}
