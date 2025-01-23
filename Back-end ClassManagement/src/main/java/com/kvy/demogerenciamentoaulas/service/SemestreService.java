package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.SemestreEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.SemestreRepository;
import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class SemestreService {
    private final SemestreRepository semestreRepository;

    @Transactional
    public Semestre salvar(SemestreDTO semestreDTO) {
        if (semestreDTO.getSemestre() == null || semestreDTO.getSemestre().isBlank()) {
            throw new IllegalArgumentException("O nome do Semestre não pode ser nulo ou vazio");
        }

        Semestre semestre = new Semestre();
        semestre.setSemestre(TratamentoDeString.capitalizeWords(semestreDTO.getSemestre()));
        return semestreRepository.save(semestre);
    }

    @Transactional
    public Semestre buscarPorId(Long id) {
        return semestreRepository.findById(id)
                .orElseThrow(() -> new SemestreEntityNotFoundException(String.format("Semestre id=%s não encontrado", id)));
    }

    @Transactional
    public Semestre editar(Long id, SemestreDTO semestreDTO) {
        Semestre existingSemestre = buscarPorId(id);

        if (semestreDTO.getSemestre() == null || semestreDTO.getSemestre().isBlank()) {
            throw new IllegalArgumentException("O nome do Semestre não pode ser nulo ou vazio");
        }

        existingSemestre.setSemestre(TratamentoDeString.capitalizeWords(semestreDTO.getSemestre()));
        return semestreRepository.save(existingSemestre);
    }
    @Transactional
    public void excluir(Long id) {
        Optional<Semestre> optionalSemestre = semestreRepository.findById(id);
        if (optionalSemestre.isPresent()) {
            semestreRepository.delete(optionalSemestre.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Semestre não encontrado com o ID: " + id);
        }
    }
    @Transactional(readOnly = true)
    public List<Semestre> buscarTodos() {
        return semestreRepository.findAll();
    }

    @PostConstruct
    @Transactional
    public void adicionarSemestrePadrao() {
        adicionarSemestreSeNaoExistir("Primeiro");
        adicionarSemestreSeNaoExistir("Segundo");
    }

    private void adicionarSemestreSeNaoExistir(String nomeSemestre) {
        if (!semestreRepository.existsBySemestre(nomeSemestre)) {
            Semestre semestre = new Semestre();
            semestre.setSemestre(nomeSemestre);
            semestreRepository.save(semestre);
        }
    }
}
