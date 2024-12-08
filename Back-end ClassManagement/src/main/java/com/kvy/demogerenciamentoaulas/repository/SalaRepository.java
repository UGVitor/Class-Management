package com.kvy.demogerenciamentoaulas.repository;


import com.kvy.demogerenciamentoaulas.entity.Sala;
import com.kvy.demogerenciamentoaulas.repository.Projection.SalaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    @Query("select s.id as id, s.numero as numero, s.capacidade as capacidade, t.tipoSala as tipoSalaNome " +
            "FROM Sala s JOIN s.tipoSala t")
    List<SalaProjection> findAllSalas();

}
