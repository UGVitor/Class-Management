package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.DiaSemana;
import com.kvy.demogerenciamentoaulas.exception.DiaSemanaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.DiaSemanaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.DiaSemanaRepository;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaSemanaService {

    private final DiaSemanaRepository diaSemanaRepository;

    public DiaSemanaDTO toDTO(DiaSemana diaSemana) {
        return new DiaSemanaDTO(diaSemana.getId(), diaSemana.getDia());
    }

    @Transactional
    public DiaSemana salvar(DiaSemanaDTO diaSemanaDTO) {

        if (diaSemanaDTO == null) {
            throw new IllegalArgumentException("DiaSemanaDTO não pode ser nulo");
        }

        String dia = diaSemanaDTO.getDia();
        if (dia == null || dia.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'dia' é obrigatório");
        }
        try {
            DiaSemana diaSemana = new DiaSemana();
            diaSemana.setDia(TratamentoDeString.capitalizeWords(diaSemanaDTO.getDia()));
            return diaSemanaRepository.save(diaSemana);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DiaSemanaUniqueViolationException(String.format("Dia da Semana '%s' já cadastrado", diaSemanaDTO.getDia()));
        }
    }

    @Transactional
    public DiaSemana buscarPorId(Long id) {
        return diaSemanaRepository.findById(id)
                .orElseThrow(() -> new DiaSemanaEntityNotFoundException(String.format("Dia da Semana id=%s não encontrado", id)));
    }

    @Transactional
    public DiaSemana editar(Long id, DiaSemanaDTO diaSemanaDTO) {
        // Validação do DTO e campos obrigatórios
        if (diaSemanaDTO == null) {
            throw new IllegalArgumentException("DiaSemanaDTO não pode ser nulo");
        }

        String dia = diaSemanaDTO.getDia();
        if (dia == null || dia.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'dia' é obrigatório");
        }
        DiaSemana existingDiaSemana = buscarPorId(id);
        existingDiaSemana.setDia(TratamentoDeString.capitalizeWords(diaSemanaDTO.getDia()));
        return diaSemanaRepository.save(existingDiaSemana);
    }

    @Transactional
    public void excluir(Long id) {
        DiaSemana optionalDiaSemana = buscarPorId(id);
        diaSemanaRepository.delete(optionalDiaSemana);
        System.out.println("Dia da Semana deletado com sucesso!");
    }

    @Transactional(readOnly = true)
    public List<DiaSemana> buscarTodos() {
        return diaSemanaRepository.findAll();
    }


    @PostConstruct
    @Transactional
    public void adicionarDiaSemanaPadrao() {
        adicionarDiaSemanaSeNaoExistir("Segunda-Feira");
        adicionarDiaSemanaSeNaoExistir("Terça-Feira");
        adicionarDiaSemanaSeNaoExistir("Quarta-Feira");
        adicionarDiaSemanaSeNaoExistir("Quinta-Feira");
        adicionarDiaSemanaSeNaoExistir("Sexta-Feira");
        adicionarDiaSemanaSeNaoExistir("Sábado");
        adicionarDiaSemanaSeNaoExistir("Domingo");

    }

    private void adicionarDiaSemanaSeNaoExistir(String dia) {
        if (!diaSemanaRepository.existsByDia(dia)) {
            DiaSemana diaSemana = new DiaSemana();
            diaSemana.setDia(dia);
            diaSemanaRepository.save(diaSemana);
        }
    }
}
