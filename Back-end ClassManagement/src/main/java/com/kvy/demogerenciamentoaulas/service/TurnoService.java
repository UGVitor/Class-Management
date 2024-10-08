package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Turno;
import com.kvy.demogerenciamentoaulas.exception.TurnoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.TurnoRepository;
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
    public Turno salvar(Turno turno) { return turnoRepository.save(turno);}

    @Transactional
    public Turno buscarPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoEntityNotFoundException(String.format("Turno id=%s não encontrado", id)));
    }

    @Transactional
    public Turno editar(Long id, Turno turno) {
        Turno existingTurno = buscarPorId(id);

        existingTurno.setTurno(turno.getTurno());
        return existingTurno;
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
    public List<Turno> buscarTodos(Long id){ return turnoRepository.findAll();}
}
