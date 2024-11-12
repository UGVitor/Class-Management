package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.PerfilEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    @Transactional
    public Perfil salvar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new PerfilEntityNotFoundException(String.format("Perfil id=%s não encontrado", id)));
    }

    @Transactional
    public Perfil editar(Long id, Perfil perfil) {
        Perfil existingPerfil = buscarPorId(id);

        existingPerfil.setNome(perfil.getNome());
        return perfilRepository.save(existingPerfil);
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Perfil> optionalPerfil = perfilRepository.findById(id);
        if (optionalPerfil.isPresent()) {
            perfilRepository.delete(optionalPerfil.get());
            System.out.println("Perfil deletado com sucesso!");
        } else {
            throw new RuntimeException("Perfil não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Perfil> buscarTodos() {
        return perfilRepository.findAll();
    }
}
