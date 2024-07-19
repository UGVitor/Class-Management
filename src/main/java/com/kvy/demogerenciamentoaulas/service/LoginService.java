package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.exception.IncorrectPasswordException;
import com.kvy.demogerenciamentoaulas.exception.LoginEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.LoginUniqueViolationException;
import com.kvy.demogerenciamentoaulas.exception.PasswordMismatchException;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Login salvar(Login login) {
        try {
            login.setPassword(passwordEncoder.encode(login.getPassword()));
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
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordMismatchException("Nova senha não confere com confirmação senha.");
        }

        Login user = buscarPorId(id);
        if (user == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new IncorrectPasswordException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        loginRepository.save(user);

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

    @Transactional(readOnly = true)
    public Login buscarPorUsername(String username) {
        return loginRepository.findByUsername(username).orElseThrow(
                () -> new LoginEntityNotFoundException(String.format("Usuário com '%s' não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public Login.Role buscarRolePorUsername(String username) {
        return loginRepository.findRoleByUsername(username);
    }
}
