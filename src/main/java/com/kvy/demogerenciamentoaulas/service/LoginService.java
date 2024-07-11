package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.exception.LoginEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.LoginUniqueViolationException;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Transactional
    public Login salvar(Login login) {
        try {
            return loginRepository.save(login);

        } catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new LoginUniqueViolationException(String.format("Login {%s} ja existente", login.getLogin()));
        }
        }

    @Transactional(readOnly = true)
    public Login buscarPorId(Long id){
        return loginRepository.findById(id).orElseThrow(
                () -> new LoginEntityNotFoundException(String.format("Usuario id=%s não encontrado", id))
        );
    }

    @Transactional
    public Login editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação senha.");
        }
        Login user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(novaSenha);
        return user;
    }

    @Transactional
    public Login editar(Long id, Login login) {
        Login existingUser = buscarPorId(id);

        existingUser.setLogin(login.getLogin());
        existingUser.setPassword(login.getPassword());
        return existingUser;
    }

    @Transactional
    public void excluir(Long id) {
        Optional<Login> optionalUser = loginRepository.findById(id);
        if (optionalUser.isPresent()) {
            loginRepository.delete(optionalUser.get());
            System.out.println("Deletado com Sucesso!");
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }

    @Transactional(readOnly = true)
    public List<Login> buscarTodos() {
        return loginRepository.findAll();
    }
}
