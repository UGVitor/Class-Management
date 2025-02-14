package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    boolean existsByHoraInicioAndHoraTermino(LocalTime horaInicio, LocalTime horaTermino);

}
