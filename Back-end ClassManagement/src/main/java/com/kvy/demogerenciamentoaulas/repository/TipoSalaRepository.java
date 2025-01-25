package com.kvy.demogerenciamentoaulas.repository;


import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoSalaRepository extends JpaRepository<TipoSala, Long> {

    boolean existsByTipoSala(String nomeTipoSala);
}
