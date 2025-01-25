package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class TurnoService {

    private final TurnoRepository turnoRepository;

    @Transactional
    public Turno salvar(TurnoDTO turnoDTO) {
        if(turnoDTO.getTurno() == null || turnoDTO.getTurno().isBlank()) {
            throw new IllegalArgumentException("O nome do Turno não pode ser nulo ou vazio.");
        }
        Turno turno = new Turno();
        turno.setTurno(TratamentoDeString.capitalizeWords(turnoDTO.getTurno()));
        return turnoRepository.save(turno);
    }

    @Transactional
    public Turno buscarPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoEntityNotFoundException(String.format("Turno id=%s não encontrado", id)));
    }

    @Transactional
    public Turno editar(Long id, TurnoDTO turnoDTO) {
        Turno existingTurno = buscarPorId(id);


        if (turnoDTO.getTurno() == null || turnoDTO.getTurno().isBlank()) {
            throw new IllegalArgumentException("O nome do Turno não pode ser nulo ou vazio");
        }
        existingTurno.setTurno(TratamentoDeString.capitalizeWords(turnoDTO.getTurno()));
        return turnoRepository.save(existingTurno);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Turno> optionalTurno = turnoRepository.findById(id);
        if (optionalTurno.isPresent()) {
            turnoRepository.delete(optionalTurno.get());
            System.out.println("Deletado com sucesso");
        } else {
            throw new RuntimeException("Turno não encontrado com o ID " +id);

        }
    }

    @Transactional(readOnly = true)
    public List<Turno> buscarTodos(){ return turnoRepository.findAll();}

    @PostConstruct
    @Transactional
    public void adicionarTurnoPadrao() {
        adicionarTurnoSeNaoExistir("Matutino");
        adicionarTurnoSeNaoExistir("Vespertino");
        adicionarTurnoSeNaoExistir("Noturno");
    }

    private void adicionarTurnoSeNaoExistir(String nomeTurno) {
        if (!turnoRepository.existsByTurno(nomeTurno)) {
            Turno turno = new Turno();
            turno.setTurno(nomeTurno);
            turnoRepository.save(turno);
        }
    }
}
