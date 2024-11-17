package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.entity.Horario;
import com.kvy.demogerenciamentoaulas.entity.Sala;
//import com.kvy.demogerenciamentoaulas.entity.DiasDaSemana;
import com.kvy.demogerenciamentoaulas.exception.AulaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.HorarioRepository;
import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
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
    //private final DiasDaSemanaRepository diasDaSemanaRepository;

    // Método para salvar uma aula
    @Transactional
    public Aula salvar(AulaDTO aulaDTO) {
        Aula aula = new Aula();

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

        // Associa o dia da semana
       // if (aulaDTO.getDiaDaSemanaId() != null) {
        //    DiasDaSemana diasDaSemana = diasDaSemanaRepository.findById(aulaDTO.getDiaDaSemanaId())
       //             .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaDaSemanaId()));
        //    aula.setDiaDaSemana(diasDaSemana);
       // } else {
      //      throw new IllegalArgumentException("O ID do dia da semana não pode ser nulo");
      //  }

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
       // if (aulaDTO.getDiaDaSemanaId() != null) {
       //     DiasDaSemana diasDaSemana = diasDaSemanaRepository.findById(aulaDTO.getDiaDaSemanaId())
       //             .orElseThrow(() -> new IllegalArgumentException("Dia da semana não encontrado com o ID: " + aulaDTO.getDiaDaSemanaId()));
      //      existingAula.setDiaDaSemana(diasDaSemana);
      //  }

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
