package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.entity.TipoSala;
import com.kvy.demogerenciamentoaulas.exception.PeriodoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.PeriodoUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.PeriodoRepository;
import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class PeriodoService {
    private final PeriodoRepository periodoRepository;

    public PeriodoDTO toDTO(Periodo periodo) {
        return new PeriodoDTO(periodo.getId(), periodo.getNome());
    }

    @Transactional
    public Periodo salvar(PeriodoDTO periodoDTO) {
        try {
            Periodo periodo = new Periodo();
            periodo.setNome(TratamentoDeString.capitalizeWords(periodoDTO.getNome()));
            return periodoRepository.save(periodo);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new PeriodoUniqueViolationException(String.format("Periodo '%s' já cadastrado", periodoDTO.getNome()));
        }

    }

    @Transactional
    public Periodo buscarPorId(Long id) {
        return periodoRepository.findById(id)
                .orElseThrow(() -> new PeriodoEntityNotFoundException(String.format("Periodo id=%s não encontrado", id)));
    }

    @Transactional
    public Periodo editar(Long id, PeriodoDTO periodoDTO) {
        Periodo existingPeriodo = buscarPorId(id);
        existingPeriodo.setNome(TratamentoDeString.capitalizeWords(periodoDTO.getNome()));
        return periodoRepository.save(existingPeriodo);
    }

    @Transactional
    public void excluir(Long id) {
        Periodo optionalPeriodo = buscarPorId(id);
        periodoRepository.delete(optionalPeriodo);
        System.out.println("Deletado com Sucesso!");
    }

    @Transactional(readOnly = true)
    public List<Periodo> buscarTodos() {
        return periodoRepository.findAll();
    }

    @PostConstruct
    @Transactional
    public void adicionarPeriodoPadrao() {
        adicionarPeriodoSeNaoExistir("Primeiro");
        adicionarPeriodoSeNaoExistir("Segundo");
        adicionarPeriodoSeNaoExistir("Terceiro");
        adicionarPeriodoSeNaoExistir("Quarto");
        adicionarPeriodoSeNaoExistir("Quinto");
        adicionarPeriodoSeNaoExistir("Sexto");
    }

    private void adicionarPeriodoSeNaoExistir(String nomePeriodo) {
        if (!periodoRepository.existsByNome(nomePeriodo)) {
            Periodo periodo = new Periodo();
            periodo.setNome(nomePeriodo);
            periodoRepository.save(periodo);
        }
    }
}
