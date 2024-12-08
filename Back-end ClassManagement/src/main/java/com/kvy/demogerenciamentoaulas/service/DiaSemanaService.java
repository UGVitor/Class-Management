package com.kvy.demogerenciamentoaulas.service;


import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.exception.DiaSemanaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.DiaSemanaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaSemanaService {

    private final DiaSemanaRepository diaSemanaRepository;

    @Transactional
    public DiaSemana salvar(DiaSemanaDTO diaSemanaDTO) {
        if (diaSemanaDTO.getDia() == null || diaSemanaDTO.getDia().isBlank()) {
            throw new IllegalArgumentException("O nome do dia não pode ser nulo ou vazio");
        }

        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setDia(TratamentoDeString.capitalizeWords(diaSemanaDTO.getDia()));
        return diaSemanaRepository.save(diaSemana);
    }

    @Transactional
    public DiaSemana buscarPorId(Long id) {
        return diaSemanaRepository.findById(id)
                .orElseThrow(() -> new DiaSemanaEntityNotFoundException(
                        String.format("Dia da Semana id=%s não encontrado", id)));
    }

    @Transactional
    public DiaSemana editar(Long id, DiaSemanaDTO diaSemanaDTO) {
        DiaSemana existingDiaSemana = buscarPorId(id);

        if (diaSemanaDTO.getDia() == null || diaSemanaDTO.getDia().isBlank()) {
            throw new IllegalArgumentException("O nome do dia não pode ser nulo ou vazio");
        }

        existingDiaSemana.setDia(TratamentoDeString.capitalizeWords(diaSemanaDTO.getDia()));
        return diaSemanaRepository.save(existingDiaSemana);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<DiaSemana> optionalDiaSemana = diaSemanaRepository.findById(id);
        if (optionalDiaSemana.isPresent()) {
            diaSemanaRepository.delete(optionalDiaSemana.get());
            System.out.println("Dia da Semana deletado com sucesso!");
        } else {
            throw new RuntimeException("Dia da Semana não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<DiaSemana> buscarTodos() {
        return diaSemanaRepository.findAll();
    }
}
