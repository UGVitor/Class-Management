package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.*;
import com.kvy.demogerenciamentoaulas.exception.*;
import com.kvy.demogerenciamentoaulas.repository.*;
import com.kvy.demogerenciamentoaulas.repository.Projection.AulaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final DisciplinaService disciplinaService;
    private final HorarioService horarioService;
    private final SalaService salaService;
    private final DiaSemanaService diaSemanaService;
    private final TurmaService turmaService;

    @Transactional
    public Aula salvar(AulaDTO aulaDTO) {
        try {
            Aula aula = new Aula();

            Disciplina disciplina = disciplinaService.buscarPorId(aulaDTO.getDisciplinaId());
            aula.setDisciplina(disciplina);

            Horario horario = horarioService.buscarPorId(aulaDTO.getHorarioId());
            aula.setHorario(horario);

            Sala sala = salaService.buscarPorId(aulaDTO.getSalaId());
            aula.setSala(sala);

            Turma turma = turmaService.buscarPorId(aulaDTO.getTurmaId());
            aula.setTurma(turma);

            DiaSemana diaSemana = diaSemanaService.buscarPorId(aulaDTO.getDiaSemanaId());
            aula.setDiaSemana(diaSemana);

            return aulaRepository.save(aula);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new AulaUniqueViolationException("Tipo sala já cadastrado");
        }

    }


    @Transactional
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new AulaEntityNotFoundException(String.format("Aula id=%s não encontrada", id)));
    }

    @Transactional
    public Aula editar(Long id, AulaDTO aulaDTO) {
        Aula aulaExistente = buscarPorId(id);

        Disciplina disciplina = disciplinaService.buscarPorId(aulaDTO.getDisciplinaId());
        aulaExistente.setDisciplina(disciplina);

        Horario horario = horarioService.buscarPorId(aulaDTO.getHorarioId());
        aulaExistente.setHorario(horario);

        Sala sala = salaService.buscarPorId(aulaDTO.getSalaId());
        aulaExistente.setSala(sala);

        Turma turma = turmaService.buscarPorId(aulaDTO.getTurmaId());
        aulaExistente.setTurma(turma);

        DiaSemana diaSemana = diaSemanaService.buscarPorId(aulaDTO.getDiaSemanaId());
        aulaExistente.setDiaSemana(diaSemana);

        return aulaRepository.save(aulaExistente);
    }


    @Transactional
    public void excluir(Long id) {
        Aula optionalAula = buscarPorId(id);
        aulaRepository.delete(optionalAula);
        System.out.println("Deletado com Sucesso!");
    }

    @Transactional(readOnly = true)
    public List<AulaProjection> buscarTodos() {
        return aulaRepository.findAllAulasWithDisciplinaNomeAndHorario();
    }


    @Transactional(readOnly = true)
    public List<AulaProjection> buscarAulasPorDia(String diaSemana) {
        return aulaRepository.findByDiaSemanaDia(diaSemana);
    }

}