package com.kvy.demogerenciamentoaulas.service;

<<<<<<< HEAD
import com.kvy.demogerenciamentoaulas.entity.Aula;
=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
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
<<<<<<< HEAD
    public Login editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("Nova senha não confere com confirmação senha.");
        }
        Login user = buscarPorId(id);
        if (!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(novaSenha);
=======
    public Login editarSenha(Long id, String password) {
        Login user = buscarPorId(id);
        user.setPassword(password);
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
        return user;
    }

    @Transactional
    public Login editar(Long id, Login login) {
        Login existingUser = buscarPorId(id);

<<<<<<< HEAD
        existingUser.setLogin(login.getLogin());
=======
        existingUser.setUsername(login.getUsername());
>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
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

<<<<<<< HEAD
    @Transactional(readOnly = true)
    public List<Login> buscarTodos() {
        return loginRepository.findAll();
    }
=======

>>>>>>> 7e2ba0f3d15dc7b62c24eeeb97c1961f193f1f98
}
