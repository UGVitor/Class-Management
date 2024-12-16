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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final HorarioRepository horarioRepository;
    private final SalaRepository salaRepository;
    private final DiaSemanaRepository diaSemanaRepository;
    private final TurmaRepository turmaRepository;

    @Transactional
    public Aula salvar(AulaDTO aulaDTO) {
        Optional<Aula> aulaExistente = aulaRepository.findByUniqueAttributes(
                aulaDTO.getDisciplinaId(),
                aulaDTO.getHorarioId(),
                aulaDTO.getSalaId(),
                aulaDTO.getTurmaId(),
                aulaDTO.getDiaSemanaId()
        );

        if (aulaExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe uma aula com os mesmos parâmetros.");
        }

        Aula aula = new Aula();

        Disciplina disciplina = disciplinaRepository.findById(aulaDTO.getDisciplinaId())
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com o ID: " + aulaDTO.getDisciplinaId()));
        aula.setDisciplina(disciplina);

        Horario horario = horarioRepository.findById(aulaDTO.getHorarioId())
                .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado com o ID: " + aulaDTO.getHorarioId()));
        aula.setHorario(horario);

        Sala sala = salaRepository.findById(aulaDTO.getSalaId())
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com o ID: " + aulaDTO.getSalaId()));
        aula.setSala(sala);

        Turma turma = turmaRepository.findById(aulaDTO.getTurmaId())
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com o ID: " + aulaDTO.getTurmaId()));
        aula.setTurma(turma);

        DiaSemana diaSemana = diaSemanaRepository.findById(aulaDTO.getDiaSemanaId())
                .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaSemanaId()));
        aula.setDiaSemana(diaSemana);

        return aulaRepository.save(aula);
    }


    @Transactional
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new AulaEntityNotFoundException(String.format("Aula id=%s não encontrada", id)));
    }

    @Transactional
    public Aula editar(Long id, AulaDTO aulaDTO) {
        Aula aulaExistente = aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada com o ID: " + id));

        Disciplina disciplina = disciplinaRepository.findById(aulaDTO.getDisciplinaId())
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com o ID: " + aulaDTO.getDisciplinaId()));
        aulaExistente.setDisciplina(disciplina);



        Horario horario = horarioRepository.findById(aulaDTO.getHorarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado com o ID: " + aulaDTO.getHorarioId()));
        aulaExistente.setHorario(horario);



        Sala sala = salaRepository.findById(aulaDTO.getSalaId())
                    .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com o ID: " + aulaDTO.getSalaId()));
        aulaExistente.setSala(sala);



        Turma turma = turmaRepository.findById(aulaDTO.getTurmaId())
                    .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com o ID: " + aulaDTO.getTurmaId()));
        aulaExistente.setTurma(turma);



        DiaSemana diaSemana = diaSemanaRepository.findById(aulaDTO.getDiaSemanaId())
                    .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaSemanaId()));
        aulaExistente.setDiaSemana(diaSemana);


        return aulaRepository.save(aulaExistente);
    }


    @Transactional
    public void excluir(Long id) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            aulaRepository.delete(optionalAula.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new AulaEntityNotFoundException(String.format("Aula id=%s não encontrada", id));
        }
    }

    @Transactional(readOnly = true)
    public List<AulaProjection> buscarTodos() {
        return aulaRepository.findAllAulasWithDisciplinaNomeAndHorario();
    }

    // Métodos auxiliares para busca
    private Disciplina buscarDisciplina(Long id) {
        return id != null ? disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaEntityNotFoundException("Disciplina não encontrada com ID: " + id)) : null;
    }

    private Horario buscarHorario(Long id) {
        return id != null ? horarioRepository.findById(id)
                .orElseThrow(() -> new HorarioEntityNotFoundException("Horário não encontrado com ID: " + id)) : null;
    }

    private Sala buscarSala(Long id) {
        return id != null ? salaRepository.findById(id)
                .orElseThrow(() -> new SalaEntityNotFoundException("Sala não encontrada com ID: " + id)) : null;
    }

    private DiaSemana buscarDiaSemana(Long id) {
        return id != null ? diaSemanaRepository.findById(id)
                .orElseThrow(() -> new DiaSemanaEntityNotFoundException("Dia da Semana não encontrado com ID: " + id)) : null;
    }

    @Transactional(readOnly = true)
    public List<AulaProjection> buscarAulasPorDia(String diaSemana) {
        return aulaRepository.findByDiaSemanaDia(diaSemana);
    }

}