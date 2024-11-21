package com.kvy.demogerenciamentoaulas.service;


import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.*;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class TurmaService {
    private TurmaRepository turmaRepository;
    private CursoRepository cursoRepository;
    private SemestreRepository semestreRepository;
    private TurnoRepository turnoRepository;
    private PeriodoRepository periodoRepository;

    @Transactional
    public Turma salvar(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        turma.setNome(turmaDTO.getNome());

        if (turmaDTO.getPeriodo() != null) {
            Periodo periodo = periodoRepository.findById(turmaDTO.getPeriodo())
                    .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + turmaDTO.getPeriodo()));
            turma.setPeriodo(periodo);
        }

        if (turmaDTO.getTurno() != null) {
            Turno turno = turnoRepository.findById(turmaDTO.getTurno())
                    .orElseThrow(() -> new RuntimeException("Turno não encontrado com o ID: " + turmaDTO.getTurno()));
            turma.setTurno(turno);
        }
        if (turmaDTO.getSemestre() != null) {
            Semestre semestre = semestreRepository.findById(turmaDTO.getSemestre())
                    .orElseThrow(() -> new RuntimeException("Semestre não encontrado com o ID: " + turmaDTO.getSemestre()));
            turma.setSemestre(semestre);
        }

        if (turmaDTO.getCurso() != null) {
            Curso curso = cursoRepository.findById(turmaDTO.getCurso())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + turmaDTO.getCurso()));
            turma.setCurso(curso);
        }
        return turmaRepository.save(turma);
    }

    @Transactional(readOnly = true)
    public Turma buscarPorId(Long id){
        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaEntityNotFoundException(String.format("Turma id=%s não encontrado", id)));
    }
    @Transactional
    public List<TurmaProjection> buscarTodasTurmasComDetalhes() {
        return turmaRepository.findAllTurmas();
    }


    @Autowired
    public TurmaService(PeriodoRepository periodoRepository, TurnoRepository turnoRepository, TurmaRepository turmaRepository ,CursoRepository cursoRepository, SemestreRepository semestreRepository) {
        this.periodoRepository = periodoRepository;
        this.turnoRepository = turnoRepository;
        this.cursoRepository = cursoRepository;
        this.semestreRepository = semestreRepository;
        this.turmaRepository = turmaRepository;
    }

    @Transactional
    public Turma editar(Long id, TurmaDTO turmaDTO) {
        Turma existingTurma = turmaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada com o ID: " + id));

        existingTurma.setNome(turmaDTO.getNome());

        if (turmaDTO.getPeriodo() != null) {
            Periodo periodo = periodoRepository.findById(turmaDTO.getPeriodo())
                    .orElseThrow(() -> new RuntimeException("Periodo não encontrado com o ID: " + turmaDTO.getPeriodo()));
            existingTurma.setPeriodo(periodo);
        }

        if (turmaDTO.getTurno() != null) {
            Turno turno = turnoRepository.findById(turmaDTO.getTurno())
                    .orElseThrow(() -> new RuntimeException("Turno não encontrado com o ID: " + turmaDTO.getTurno()));
            existingTurma.setTurno(turno);
        }
        if (turmaDTO.getSemestre() != null) {
            Semestre semestre = semestreRepository.findById(turmaDTO.getSemestre())
                    .orElseThrow(() -> new RuntimeException("Semestre não encontrado com o ID: " + turmaDTO.getSemestre()));
            existingTurma.setSemestre(semestre);
        }

        if (turmaDTO.getCurso() != null) {
            Curso curso = cursoRepository.findById(turmaDTO.getCurso())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + turmaDTO.getCurso()));
            existingTurma.setCurso(curso);
        }


        return turmaRepository.save(existingTurma);
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

