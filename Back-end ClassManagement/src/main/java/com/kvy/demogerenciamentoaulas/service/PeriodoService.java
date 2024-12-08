package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.PeriodoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.PeriodoRepository;
import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;
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
    public Periodo salvar(PeriodoDTO periodoDTO) {
        if (periodoDTO.getNome() == null || periodoDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do dia não pode ser nulo ou vazio");
        }
        Periodo periodo = new Periodo();
        periodo.setNome(TratamentoDeString.capitalizeWords(periodoDTO.getNome()));

        return periodoRepository.save(periodo);
    }

    @Transactional
    public Periodo buscarPorId(Long id) {
        return periodoRepository.findById(id)
                .orElseThrow(() -> new PeriodoEntityNotFoundException(String.format("Periodo id=%s não encontrado", id)));
    }

    @Transactional
    public Periodo editar(Long id, PeriodoDTO periodoDTO) {
        Periodo existingPeriodo = buscarPorId(id);


        if (periodoDTO.getNome() == null || periodoDTO.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do dia não pode ser nulo ou vazio");
        }

        existingPeriodo.setNome(TratamentoDeString.capitalizeWords(periodoDTO.getNome()));
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
