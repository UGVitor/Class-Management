package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.exception.DiaSemanaUniqueViolationException;
import com.kvy.demogerenciamentoaulas.exception.DisciplinaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.ProfInvalidException;
import com.kvy.demogerenciamentoaulas.repository.DisciplinaRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.DisciplinaProjection;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DisciplinaService {

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaService.class);

    private final DisciplinaRepository disciplinaRepository;
    private final LoginService loginService;

    @Transactional
    public Disciplina salvar(DisciplinaDTO disciplinaDTO) {
        // Validação do DTO e campos obrigatórios
        if (disciplinaDTO == null) {
            throw new IllegalArgumentException("DisciplinaDTO não pode ser nulo");
        }

        String nomeDisciplina = disciplinaDTO.getNome();
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da disciplina é obrigatório");
        }
        try {
            Login login = loginService.buscarPorId(disciplinaDTO.getLoginId());

            // Verifica se o perfil do login é do tipo "Professor"
            if (!"Professor".equalsIgnoreCase(login.getPerfil().getNome())) {
                throw new ProfInvalidException("A disciplina só pode ser criada por um login com perfil de 'Professor'");
            }

            Disciplina disciplina = new Disciplina();
            disciplina.setNome(TratamentoDeString.capitalizeWords(disciplinaDTO.getNome()));
            disciplina.setLogin(login);

            return disciplinaRepository.save(disciplina);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DiaSemanaUniqueViolationException(String.format("Disciplina '%s' já cadastrado", disciplinaDTO.getNome()));
        }

    }

    @Transactional(readOnly = true)
    public Disciplina buscarPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaEntityNotFoundException(
                        String.format("Disciplina com id=%s não encontrada", id)));
    }

    @Transactional
    public Disciplina editar(Long id, DisciplinaDTO disciplinaDTO) {
        // Validação do DTO e campos obrigatórios
        if (disciplinaDTO == null) {
            throw new IllegalArgumentException("DisciplinaDTO não pode ser nulo");
        }

        if (disciplinaDTO.getLoginId() == null) {
            throw new IllegalArgumentException("O ID do login é obrigatório");
        }

        String nomeDisciplina = disciplinaDTO.getNome();
        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da disciplina é obrigatório");
        }
        Disciplina existingDisciplina = buscarPorId(id);
        existingDisciplina.setNome(TratamentoDeString.capitalizeWords(disciplinaDTO.getNome()));

        Login login = loginService.buscarPorId(disciplinaDTO.getLoginId());
        if (!"Professor".equalsIgnoreCase(login.getPerfil().getNome())) {
            throw new ProfInvalidException("A disciplina só pode ser associada a um login com perfil de 'Professor'");
        }

        existingDisciplina.setLogin(login);

        return disciplinaRepository.save(existingDisciplina);
    }



    @Transactional
    public void excluir(Long id) {
        Disciplina disciplina = buscarPorId(id);
        disciplinaRepository.delete(disciplina);
        System.out.println("Disciplina excluida com sucesso");
    }

    @Transactional(readOnly = true)
    public List<DisciplinaProjection> buscarTodos() {
        return disciplinaRepository.findAllDisciplinaWithLoginAndTurma();
    }
}
