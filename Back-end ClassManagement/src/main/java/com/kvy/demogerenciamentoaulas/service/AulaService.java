package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.*;
//import com.kvy.demogerenciamentoaulas.entity.DiasDaSemana;
import com.kvy.demogerenciamentoaulas.exception.AulaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.*;
//import com.kvy.demogerenciamentoaulas.repository.DiasDaSemanaRepository;
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

    // Método para salvar uma aula
    @Transactional
    public Aula salvar(AulaDTO aulaDTO) {
        Aula aula = new Aula();


        aula.setDescricao(aulaDTO.getDescricao());
        // Associa a disciplina
        if (aulaDTO.getDisciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(aulaDTO.getDisciplinaId())
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com o ID: " + aulaDTO.getDisciplinaId()));
            aula.setDisciplina(disciplina);
        } else {
            throw new IllegalArgumentException("O ID da disciplina não pode ser nulo");
        }

        // Associa o horário
        if (aulaDTO.getHorarioId() != null) {
            Horario horario = horarioRepository.findById(aulaDTO.getHorarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado com o ID: " + aulaDTO.getHorarioId()));
            aula.setHorario(horario);
        } else {
            throw new IllegalArgumentException("O ID do horário não pode ser nulo");
        }

        if (aulaDTO.getDiaSemanaId() != null) {
            DiaSemana diaSemana = diaSemanaRepository.findById(aulaDTO.getDiaSemanaId())
                   .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaSemanaId()));
            aula.setDiaSemana(diaSemana);
        } else {
            throw new IllegalArgumentException("O ID do dia da semana não pode ser nulo");
        }

        // Associa a sala
        if (aulaDTO.getSalaId() != null) {
            Sala sala = salaRepository.findById(aulaDTO.getSalaId())
                    .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com o ID: " + aulaDTO.getSalaId()));
            aula.setSala(sala);
        } else {
            throw new IllegalArgumentException("O ID da sala não pode ser nulo");
        }

        return aulaRepository.save(aula);
    }

    // Método para buscar aula por ID
    @Transactional(readOnly = true)
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id)
                .orElseThrow(() -> new AulaEntityNotFoundException(String.format("Aula id=%s não encontrada", id)));
    }

    // Método para editar uma aula
    @Transactional
    public Aula editar(Long id, AulaDTO aulaDTO) {
        Aula existingAula = buscarPorId(id);

        existingAula.setDescricao(aulaDTO.getDescricao());

        // Atualiza a disciplina
        if (aulaDTO.getDisciplinaId() != null) {
            Disciplina disciplina = disciplinaRepository.findById(aulaDTO.getDisciplinaId())
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com o ID: " + aulaDTO.getDisciplinaId()));
            existingAula.setDisciplina(disciplina);
        }

        // Atualiza o horário
        if (aulaDTO.getHorarioId() != null) {
            Horario horario = horarioRepository.findById(aulaDTO.getHorarioId())
                    .orElseThrow(() -> new IllegalArgumentException("Horário não encontrado com o ID: " + aulaDTO.getHorarioId()));
            existingAula.setHorario(horario);
        }

        // Atualiza o dia da semana
        if (aulaDTO.getDiaSemanaId() != null) {
            DiaSemana diasDaSemana = diaSemanaRepository.findById(aulaDTO.getDiaSemanaId())
                   .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaSemanaId()));
            existingAula.setDiaSemana(diasDaSemana);
        }

        // Atualiza a sala
        if (aulaDTO.getSalaId() != null) {
            Sala sala = salaRepository.findById(aulaDTO.getSalaId())
                    .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com o ID: " + aulaDTO.getSalaId()));
            existingAula.setSala(sala);
        }

        return aulaRepository.save(existingAula);
    }

    // Método para excluir uma aula
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

    // Método para buscar todas as aulas
    @Transactional(readOnly = true)
    public List<AulaProjection> buscarTodos() {
        return aulaRepository.findAllAulasWithDisciplinaNomeAndHorario();
    }
}
