package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
}
