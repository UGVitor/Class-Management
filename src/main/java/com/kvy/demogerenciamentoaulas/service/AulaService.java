package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public Aula salvar(Aula aula) {
        Disciplina disciplina = disciplinaRepository.findById(aula.getCod_disciplina())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + aula.getCod_disciplina()));
        aula.setDisciplina(disciplina);
        return aulaRepository.save(aula);
    }

    @Transactional(readOnly = true)
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada com o ID: " + id));
    }

    @Transactional
    public Aula editar(Long id, Aula aula) {
        Aula existingAula = buscarPorId(id);

        existingAula.setData(aula.getData());
        existingAula.setHorario(aula.getHorario());
        existingAula.setDuracao(aula.getDuracao());
        existingAula.setTopico(aula.getTopico());
        existingAula.setCod_disciplina(aula.getCod_disciplina());

        Long novoIdDisciplina = aula.getCod_disciplina();

        if (existingAula.getDisciplina() == null ||
                !existingAula.getDisciplina().getId().equals(novoIdDisciplina)) {
            if (novoIdDisciplina != null) {
                Disciplina disciplina = disciplinaRepository.findById(aula.getCod_disciplina())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + aula.getCod_disciplina()));
                existingAula.setDisciplina(disciplina);
            } else {
                existingAula.setDisciplina(null);
            }
        }

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
}
