package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.LoginEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.LoginProjection;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.CreateLoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginSenhaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PerfilRepository perfilRepository;

    public LoginDTO convertToDTO(Login login) {
        return new LoginDTO(login.getId(), login.getLogin(), login.getPerfil().getId());
    }

    public LoginSenhaDTO convertToSenhaDTO(String senhaAtual, String novaSenha, String confirmaSenha){
        return new LoginSenhaDTO(senhaAtual, novaSenha, confirmaSenha);
    }

    public Login convertToEntity(LoginDTO loginDTO) {
        Login login = new Login();
        login.setId(loginDTO.getId());
        login.setLogin(loginDTO.getLogin());

        Perfil perfil = perfilRepository.findById(loginDTO.getPerfil())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + loginDTO.getPerfil()));
        login.setPerfil(perfil);

        return login;
    }

    public Login convertToEntityCreate(CreateLoginDTO createLoginDTO) {
        Login login = new Login();
        login.setLogin(createLoginDTO.getLogin());
        login.setPassword(createLoginDTO.getPassword());

        Perfil perfil = perfilRepository.findById(createLoginDTO.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + createLoginDTO.getPerfilId()));
        login.setPerfil(perfil);

        return login;
    }



    @Transactional
    public Login salvar(Login login) {
        return loginRepository.save(login);
    }

    @Transactional(readOnly = true)
    public Login buscarPorId(Long id){
        return loginRepository.findById(id).orElseThrow(
                () -> new LoginEntityNotFoundException(String.format("Usuario id=%s não encontrado", id))
        );
    }

    @Transactional
    public Login editarSenha(Long id, LoginSenhaDTO loginSenhaDTO) {
        if (!loginSenhaDTO.getNovaSenha().equals(loginSenhaDTO.getConfirmaSenha())){
            throw new RuntimeException("Nova senha não confere com confirmação senha.");
        }
        Login user = buscarPorId(id);
        if (!user.getPassword().equals(loginSenhaDTO.getSenhaAtual())){
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(loginSenhaDTO.getNovaSenha());
        return user;
    }

    @Transactional
    public Login editar(Long id, Login login) {
        Login existingUser = buscarPorId(id);
        existingUser.setLogin(login.getLogin());
        existingUser.setPerfil(login.getPerfil());
        return loginRepository.save(existingUser);
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
    public List<LoginProjection> buscarTodos() {
        return loginRepository.findAllLoginsWithPerfilNome();
    }


    public boolean validateLogin(String login, String password) {
        Login user = loginRepository.findByLogin(login);
        return user != null && user.getPassword().equals(password);
    }
}
