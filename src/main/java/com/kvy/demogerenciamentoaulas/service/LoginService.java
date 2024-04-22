package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Transactional
    public Login salvar(Login login) {
        return loginRepository.save(login);
    }

    @Transactional(readOnly = true)
    public Login buscarPorId(Long id){
        return loginRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }

    @Transactional
    public Login editarSenha(Long id, String password) {
        Login user = buscarPorId(id);
        user.setPassword(password);
        return user;
    }

    @Transactional
    public Login editar(Long id, Login login) {
        Login existingUser = buscarPorId(id);

        existingUser.setUsername(login.getUsername());
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


}
