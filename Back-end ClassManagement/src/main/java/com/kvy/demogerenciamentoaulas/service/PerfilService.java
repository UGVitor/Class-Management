package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.PerfilEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import com.kvy.demogerenciamentoaulas.web.dto.PerfilDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilDTO convertToDTO(Perfil perfil) {
        return new PerfilDTO(perfil.getId(), perfil.getNome());
    }

    public Perfil convertToEntity(PerfilDTO perfilDTO) {
        Perfil perfil = new Perfil();
        perfil.setId(perfilDTO.getId());
        perfil.setNome(perfilDTO.getNome());
        return perfil;
    }

    @Transactional
    public Perfil salvar(Perfil perfil) {
        perfil.setNome(TratamentoDeString.capitalizeWords(perfil.getNome()));
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

        existingPerfil.setNome(TratamentoDeString.capitalizeWords(perfil.getNome()));
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

<<<<<<< HEAD
=======

    @PostConstruct
    @Transactional
    public void adicionarPerfisPadrao() {
        adicionarPerfilSeNaoExistir("Professor");
        adicionarPerfilSeNaoExistir("Admin");
    }

    private void adicionarPerfilSeNaoExistir(String nomePerfil) {
        if (!perfilRepository.existsByNome(nomePerfil)) {
            Perfil perfil = new Perfil();
            perfil.setNome(nomePerfil);
            salvar(perfil);
        }
    }
>>>>>>> 6e87bf9130b1a250c56f4746833d3d03555a5244
}
