package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

}
