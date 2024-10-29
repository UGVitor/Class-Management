package com.kvy.demogerenciamentoaulas.repository;

import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {
    Optional<Modalidade> findByNome(String nome);
}
