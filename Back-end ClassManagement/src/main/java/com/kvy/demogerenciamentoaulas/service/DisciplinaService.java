package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DisciplinaService {

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaService.class);

    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public Disciplina salvar(Disciplina disciplina) {
        if (disciplinaRepository.existsByNome(disciplina.getNome())) {
            throw new DisciplinaUniqueViolationException(
                    String.format("Já existe uma disciplina com o nome '%s'", disciplina.getNome()));
        }
        return disciplinaRepository.save(disciplina);
    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaEntityNotFoundException(
                        String.format("Disciplina com id=%s não encontrada", id)));
    }

    @Transactional
    public Disciplina editar(Long id, Disciplina disciplina) {
        Disciplina existingDisciplina = buscarPorId(id);
        existingDisciplina.setNome(disciplina.getNome());
        existingDisciplina.setDescricao(disciplina.getDescricao());
        existingDisciplina.setLogin(disciplina.getLogin());
        existingDisciplina.setTurma(disciplina.getTurma());
        return disciplinaRepository.save(existingDisciplina);
    }

    @Transactional
    public void excluir(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new DisciplinaEntityNotFoundException(
                    String.format("Disciplina com id=%s não encontrada", id));
        }
        disciplinaRepository.deleteById(id);
        logger.info("Disciplina com id={} excluída com sucesso", id);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaProjection> buscarTodos() {
        return disciplinaRepository.findAllDisciplinaWithLoginAndTurma();
    }
}
