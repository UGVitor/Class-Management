package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.exception.AulaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public Aula salvar(Aula aula) {
        Disciplina disciplina = disciplinaRepository.findById(aula.getDisciplina().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + aula.getDisciplina().getId()));
        aula.setDisciplina(disciplina);
        return aulaRepository.save(aula);
    }

    @Transactional(readOnly = true)
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new AulaEntityNotFoundException(String.format("Aula id=%s não encontrado", id)));
    }


    @Transactional
    public Aula editar(Long id, Aula aula) {
        Aula existingAula = buscarPorId(id);

        existingAula.setData(aula.getData());
        existingAula.setHorario(aula.getHorario());
        existingAula.setDuracao(aula.getDuracao());
        existingAula.setTopico(aula.getTopico());

        Disciplina disciplina = disciplinaRepository.findById(aula.getDisciplina().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + aula.getDisciplina().getId()));
        existingAula.setDisciplina(disciplina);

        return existingAula;
    }


    @Transactional
    public void excluir(Long id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            aulaRepository.delete(optionalAula.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Aula não encontrada com o ID: " + id);
        }
    }


    @Transactional(readOnly = true)
    public List<Aula> buscarTodos() {
        return aulaRepository.findAll();
    }

}
