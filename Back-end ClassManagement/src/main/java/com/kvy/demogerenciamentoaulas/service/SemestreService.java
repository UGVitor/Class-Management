package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.exception.SemestreEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.SemestreRepository;
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
    public Semestre salvar(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    @Transactional
    public Semestre buscarPorId(Long id) {
        return semestreRepository.findById(id)
                .orElseThrow(() -> new SemestreEntityNotFoundException(String.format("Semestre id=%s não encontrado", id)));
    }

    @Transactional
    public Semestre editar(Long id, Semestre semestre) {
        Semestre existingSemestre = buscarPorId(id);

        existingSemestre.setSemestre(semestre.getSemestre());
        return existingSemestre;
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
}
