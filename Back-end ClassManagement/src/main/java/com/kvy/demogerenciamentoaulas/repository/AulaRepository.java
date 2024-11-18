package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.repository.Projection.AulaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("SELECT a.id AS id, a.descricao AS descricao, " +
            "d.id AS disciplinaId, d.nome AS disciplinaNome, " +
            "s.id AS salaId, s.sala AS salaNome, s.numero AS salaNumero," +
            "h.id AS horarioId, h.horaInicio AS horarioInicio, h.horaTermino AS horarioTermino, " +
            "ds.id AS diasDaSemanaId, ds.dia AS diaSemanaNome " +
            "FROM Aula a " +
            "JOIN a.disciplina d " +
            "JOIN a.sala s " +
            "JOIN a.horario h " +
            "JOIN a.diaSemana ds")
    List<AulaProjection> findAllAulasWithDisciplinaNomeAndHorario();
}
