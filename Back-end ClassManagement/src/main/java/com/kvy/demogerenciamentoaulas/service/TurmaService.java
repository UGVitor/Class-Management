package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.*;
import com.kvy.demogerenciamentoaulas.exception.TurmaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.TurmaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.*;
import com.kvy.demogerenciamentoaulas.repository.Projection.TurmaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;
import jakarta.annotation.PostConstruct;
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
        if (turmaDTO == null) {
            throw new IllegalArgumentException("TurmaDTO não pode ser nulo");
        }

        if (turmaDTO.getNome() == null || turmaDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da turma é obrigatório");
        }

        if (turmaDTO.getPeriodo() == null) {
            throw new IllegalArgumentException("O período da turma é obrigatório");
        }

        if (turmaDTO.getTurno() == null) {
            throw new IllegalArgumentException("O turno da turma é obrigatório");
        }

        if (turmaDTO.getSemestre() == null) {
            throw new IllegalArgumentException("O semestre da turma é obrigatório");
        }

        if (turmaDTO.getCurso() == null) {
            throw new IllegalArgumentException("O curso da turma é obrigatório");
        }

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
        if (turmaDTO == null) {
            throw new IllegalArgumentException("TurmaDTO não pode ser nulo");
        }

        if (turmaDTO.getNome() == null || turmaDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da turma é obrigatório");
        }

        if (turmaDTO.getPeriodo() == null) {
            throw new IllegalArgumentException("O período da turma é obrigatório");
        }

        if (turmaDTO.getTurno() == null) {
            throw new IllegalArgumentException("O turno da turma é obrigatório");
        }

        if (turmaDTO.getSemestre() == null) {
            throw new IllegalArgumentException("O semestre da turma é obrigatório");
        }

        if (turmaDTO.getCurso() == null) {
            throw new IllegalArgumentException("O curso da turma é obrigatório");
        }


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

    @PostConstruct
    @Transactional
    public void adicionarTurmaPadrao() {
        adicionarTurmaSeNaoExistir("Turma", 5L, 1L, 2L, 1L);
    }

    public void adicionarTurmaSeNaoExistir(String nomeTurma, Long id_periodo, Long id_turno, Long id_semestre, Long id_curso) {
        if (!turmaRepository.existsByNome(nomeTurma)) {
            TurmaDTO turmaDTO = new TurmaDTO();
            turmaDTO.setNome(nomeTurma);
            turmaDTO.setPeriodo(id_periodo);
            turmaDTO.setTurno(id_turno);
            turmaDTO.setSemestre(id_semestre);
            turmaDTO.setCurso(id_curso);
            salvar(turmaDTO);
        }
    }

}

