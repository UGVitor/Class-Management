package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Horario;
import com.kvy.demogerenciamentoaulas.exception.HorarioEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;

    @Transactional
    public Horario salvar(Horario horario) {
        return horarioRepository.save(horario);
    }

    @Transactional(readOnly = true)
    public Horario buscarPorId(Long id) {
        return horarioRepository.findById(id)
                .orElseThrow(() -> new HorarioEntityNotFoundException(String.format("Horario id=%s não encontrado", id)));
    }

    @Transactional
    public Horario editar(Long id, Horario horario) {
        Horario existingHorario = buscarPorId(id);

        existingHorario.setHoraInicio(horario.getHoraInicio());
        existingHorario.setHoraTermino(horario.getHoraTermino());
        return horarioRepository.save(existingHorario);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Horario> optionalHorario = horarioRepository.findById(id);
        if (optionalHorario.isPresent()) {
            horarioRepository.delete(optionalHorario.get());
            System.out.println("Horario deletado com sucesso!");
        } else {
            throw new RuntimeException("Horario não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Horario> buscarTodos() {
        return horarioRepository.findAll();
    }
}
