package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.PerfilEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.PerfilUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import com.kvy.demogerenciamentoaulas.web.dto.PerfilDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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
    public Perfil salvar(PerfilDTO perfilDTO) {

        if (perfilDTO == null) {
            throw new IllegalArgumentException("Perfil não pode ser nulo");
        }

        if (perfilDTO.getNome() == null) {
            throw new IllegalArgumentException("O nome do Perfil é obrigatório");
        }
        try {
            Perfil perfil = new Perfil();
            perfil.setNome(TratamentoDeString.capitalizeWords(perfilDTO.getNome()));
            return perfilRepository.save(perfil);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new PerfilUniqueViolationException(String.format("Tipo sala '%s' já cadastrado", perfilDTO.getNome()));
        }

    }

    @Transactional(readOnly = true)
    public Perfil buscarPorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new PerfilEntityNotFoundException(String.format("Perfil id=%s não encontrado", id)));
    }

    @Transactional
    public Perfil editar(Long id, Perfil perfil) {
        if (perfil == null) {
            throw new IllegalArgumentException("Perfil não pode ser nulo");
        }

        if (perfil.getNome() == null || perfil.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do Perfil é obrigatório");
        }
        Perfil existingPerfil = buscarPorId(id);

        existingPerfil.setNome(TratamentoDeString.capitalizeWords(perfil.getNome()));
        return perfilRepository.save(existingPerfil);
    }

    @Transactional
    public void excluir(Long id) {
        Perfil optionalPerfil = buscarPorId(id);
        perfilRepository.delete(optionalPerfil);
        System.out.println("Perfil deletado com sucesso!");
    }

    @Transactional(readOnly = true)
    public List<Perfil> buscarTodos() {
        return perfilRepository.findAll();
    }

    @PostConstruct
    @Transactional
    public void adicionarPerfisPadrao() {
        adicionarPerfilSeNaoExistir("PROFESSOR");
        adicionarPerfilSeNaoExistir("ADMIN");
        adicionarPerfilSeNaoExistir("FISCAL DE CORREDOR");
    }

    private void adicionarPerfilSeNaoExistir(String nomePerfil) {
        if (!perfilRepository.existsByNome(nomePerfil)) {
            Perfil perfil = new Perfil();
            perfil.setNome(nomePerfil);
            perfilRepository.save(perfil);
        }
    }

}
