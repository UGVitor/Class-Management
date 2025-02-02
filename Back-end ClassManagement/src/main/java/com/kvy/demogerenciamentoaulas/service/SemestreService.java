package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.SemestreEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.SemestreUniqueViolationException;
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

    public SemestreDTO toDTO(Semestre semestre) {
        return new SemestreDTO(semestre.getId(), semestre.getSemestre());
    }

    @Transactional
    public Semestre salvar(SemestreDTO semestreDTO) {
        if (semestreDTO == null) {
            throw new IllegalArgumentException("Semestre não pode ser nulo");
        }

        if (semestreDTO.getSemestre() == null || semestreDTO.getSemestre().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do semestre é obrigatório");
        }
        try {
            Semestre semestre = new Semestre();
            semestre.setSemestre(TratamentoDeString.capitalizeWords(semestreDTO.getSemestre()));
            return semestreRepository.save(semestre);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new SemestreUniqueViolationException(String.format("Semestre '%s' já cadastrado", semestreDTO.getSemestre()));
        }

    }

    @Transactional
    public Semestre buscarPorId(Long id) {
        return semestreRepository.findById(id)
                .orElseThrow(() -> new SemestreEntityNotFoundException(String.format("Semestre id=%s não encontrado", id)));
    }

    @Transactional
    public Semestre editar(Long id, SemestreDTO semestreDTO) {
        if (semestreDTO == null) {
            throw new IllegalArgumentException("Semestre não pode ser nulo");
        }

        if (semestreDTO.getSemestre() == null || semestreDTO.getSemestre().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do semestre é obrigatório");
        }
        Semestre existingSemestre = buscarPorId(id);
        existingSemestre.setSemestre(TratamentoDeString.capitalizeWords(semestreDTO.getSemestre()));
        return semestreRepository.save(existingSemestre);
    }
    @Transactional
    public void excluir(Long id) {

        Semestre optionalSemestre = buscarPorId(id);
        semestreRepository.delete(optionalSemestre);
        System.out.println("Deletado com Sucesso!");
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
