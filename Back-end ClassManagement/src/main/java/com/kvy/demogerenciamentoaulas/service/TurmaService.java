package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Turma;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Semestre;
import com.kvy.demogerenciamentoaulas.entity.Periodo;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TurmaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.*;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final CursoService cursoService;
    private final SemestreService semestreService;
    private final TurnoService turnoService;
    private final PeriodoService periodoService;

    @Transactional
    public Turma salvar(TurmaDTO turmaDTO) {
        try {
            String nomeFormatado = TratamentoDeString.convertToUpperCase(turmaDTO.getNome());

            Turma turma = new Turma();
            turma.setNome(nomeFormatado);

            Periodo periodo = periodoService.buscarPorId(turmaDTO.getPeriodo());
            turma.setPeriodo(periodo);

            Turno turno = turnoService.buscarPorId(turmaDTO.getTurno());
            turma.setTurno(turno);

            Semestre semestre = semestreService.buscarPorId(turmaDTO.getSemestre());
            turma.setSemestre(semestre);

            Curso curso = cursoService.buscarPorId(turmaDTO.getCurso());
            turma.setCurso(curso);

            return turmaRepository.save(turma);

        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new TurmaUniqueViolationException(String.format("Turma '%s' já cadastrado", turmaDTO.getNome()));
        }

    }

    @Transactional(readOnly = true)
    public Turma buscarPorId(Long id){
        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaEntityNotFoundException(String.format("Turma id=%s não encontrado", id)));
    }


    @Transactional
    public List<TurmaProjection> buscarTodasTurmas() {
        return turmaRepository.findAllTurmasWithPeriodoAndTurnoAndCursoAndSemestre();
    }


    @Transactional
    public Turma editar(Long id, TurmaDTO turmaDTO) {

        Turma existingTurma = buscarPorId(id);

        existingTurma.setNome(TratamentoDeString.capitalizeWords(turmaDTO.getNome()));

        Periodo periodo = periodoService.buscarPorId(turmaDTO.getPeriodo());
        existingTurma.setPeriodo(periodo);

        Turno turno = turnoService.buscarPorId(turmaDTO.getTurno());
        existingTurma.setTurno(turno);

        Semestre semestre = semestreService.buscarPorId(turmaDTO.getSemestre());
        existingTurma.setSemestre(semestre);

        Curso curso = cursoService.buscarPorId(turmaDTO.getCurso());
        existingTurma.setCurso(curso);

        return turmaRepository.save(existingTurma);
    }

    @Transactional
    public void excluir(Long id){
        Turma optionalTurma = buscarPorId(id);
        turmaRepository.delete(optionalTurma);
        System.out.println("Deletado com sucesso!");
    }

}

