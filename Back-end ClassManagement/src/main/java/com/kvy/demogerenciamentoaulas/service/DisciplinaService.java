package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final LoginRepository loginRepository;

    @Transactional
    public Disciplina salvar(Disciplina disciplina) {
        Login professor = loginRepository.findById(disciplina.getProfessor().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + disciplina.getProfessor().getId()));

        if (professor.getRole() != Login.Role.PROFESSOR) {
            throw new IllegalArgumentException("O usuário associado deve ter o papel de PROFESSOR.");
        }

        disciplina.setProfessor(professor);

        return disciplinaRepository.save(disciplina);
    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaEntityNotFoundException(String.format("Disciplina id=%s não encontrado", id)));
    }

    @Transactional
    public Disciplina editar(Long id, Disciplina disciplina) {
        Disciplina existingDisciplina = buscarPorId(id);

        existingDisciplina.setNome(disciplina.getNome());
        existingDisciplina.setDescricao(disciplina.getDescricao());
        existingDisciplina.setCargaHoraria(disciplina.getCargaHoraria());
        Login professor = loginRepository.findById(disciplina.getProfessor().getId())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + disciplina.getProfessor().getId()));
        existingDisciplina.setProfessor(professor);

        return existingDisciplina;
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);
        if (optionalDisciplina.isPresent()) {
            disciplinaRepository.delete(optionalDisciplina.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Disciplina não encontrada com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Disciplina> buscarTodos(Long id) {
        return disciplinaRepository.findAll();
    }

}
