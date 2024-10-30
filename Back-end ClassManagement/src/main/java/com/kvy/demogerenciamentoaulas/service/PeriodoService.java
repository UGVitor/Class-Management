package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.PeriodoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.PeriodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class PeriodoService {
    private final PeriodoRepository periodoRepository;


    @Transactional
    public Periodo salvar(Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    @Transactional
    public Periodo buscarPorId(Long id) {
        return periodoRepository.findById(id)
                .orElseThrow(() -> new PeriodoEntityNotFoundException(String.format("Periodo id=%s não encontrado", id)));
    }

    @Transactional
    public Periodo editar(Long id, Periodo periodo) {
        Periodo existingPeriodo = buscarPorId(id);

        existingPeriodo.setNome(periodo.getNome());
        return periodoRepository.save(existingPeriodo);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Periodo> optionalPeriodo = periodoRepository.findById(id);
        if (optionalPeriodo.isPresent()) {
            periodoRepository.delete(optionalPeriodo.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Periodo não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Periodo> buscarTodos() {
        return periodoRepository.findAll();
    }
}
