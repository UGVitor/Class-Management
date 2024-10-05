package com.kvy.demogerenciamentoaulas.service;


import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.CursoRepository;
import com.kvy.demogerenciamentoaulas.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TurmaService {
    private TurmaRepository turmaRepository;
    private CursoRepository cursoRepository;

    @Transactional
    public Turma salvar(Turma turma) {
        /*
        Periodo periodo = periodoRepository.findById(turma.getPeriodo().getId())
                .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + turma.getPeriodo().getId()));
        turma.setPeriodo(periodo);
        Turno turno = turnoRepository.findById(turma.getTurno().getId())
                .orElseThrow(() -> new RuntimeException("Turno não encontrado com o ID: " + turma.getTurno().getId()));
        turma.setTurno(turno);

        Semestre semestre = semestreRepository.findById(turma.getSemestre().getId())
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado com o ID: " + turma.getSemestre().getId()));
        turma.setSemestre(semestre);
        */
        Curso curso = cursoRepository.findById(turma.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + turma.getCurso().getId()));
        turma.setCurso(curso);

        return turmaRepository.save(turma);
    }

    @Transactional(readOnly = true)
    public Turma buscarPorId(Long id){
        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaEntityNotFoundException(String.format("Turma id=%s não encontrado", id)));
    }

    @Transactional(readOnly = true)
    public List<Turma> buscarTodos(Long id) {
        return turmaRepository.findAll();
    }




    @Transactional
    public Turma editar(Long id, Turma turma){
        Turma existingTurma = buscarPorId(id);
        /*
        Periodo periodo = periodoRepository.findById(turma.getPeriodo().getId())
                .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + turma.getPeriodo().getId()));
        existingTurma.setPeriodo(periodo);
        Turno turno = turnoRepository.findById(turma.getTurno().getId())
                .orElseThrow(() -> new RuntimeException("Turno não encontrado com o ID: " + turma.getTurno().getId()));
        existingTurma.setTurno(turno);

        Semestre semestre = semestreRepository.findById(turma.getSemestre().getId())
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado com o ID: " + turma.getSemestre().getId()));
        existingTurma.setSemestre(semestre);
        */
        Curso curso = cursoRepository.findById(turma.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + turma.getCurso().getId()));
        existingTurma.setCurso(curso);

        existingTurma.setNome(turma.getNome());

        return existingTurma;
    }

    @Transactional
    public void excluir(Long id){
        Optional<Turma> optionalTurma = turmaRepository.findById(id);
        if (optionalTurma.isPresent()) {
            turmaRepository.delete(optionalTurma.get());
            System.out.println("Deletado com sucesso!");
        } else {
            throw new RuntimeException("Turma não encontrada com o ID: " + id);
        }

    }

}

