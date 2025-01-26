package com.kvy.demogerenciamentoaulas.service;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.LoginEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.exception.LoginUniqueViolationException;
import com.kvy.demogerenciamentoaulas.exception.PasswordInvalidException;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.LoginProjection;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.CreateLoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginSenhaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

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
        try{
            login.setPassword(passwordEncoder.encode(login.getPassword()));
            return loginRepository.save(login);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new LoginUniqueViolationException(String.format("Username '%s' já cadastrado", login.getLogin()));
        }

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
            throw new PasswordInvalidException("Nova senha não confere com confirmação senha.");
        }

        Login user = buscarPorId(id);
        if (!loginSenhaDTO.getSenhaAtual().equals(user.getPassword())){
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(passwordEncoder.encode(loginSenhaDTO.getNovaSenha()));
        return user;
    }

    @Transactional
    public Login editar(Long id, LoginDTO login) {
        Login existingUser = buscarPorId(id);
        existingUser.setLogin(login.getLogin());

        Perfil perfil = perfilRepository.findById(login.getPerfil())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + login.getPerfil()));
        existingUser.setPerfil(perfil);
        return loginRepository.save(existingUser);
    }

    @Transactional
    public void excluir(Long id) {
        Login optionalUser = buscarPorId(id);
        loginRepository.delete(optionalUser);
        System.out.println("Deletado com Sucesso!");
    }

    @Transactional(readOnly = true)
    public List<LoginProjection> buscarTodos() {
        return loginRepository.findAllLoginsWithPerfilNome();
    }

    @Transactional(readOnly = true)
    public List<LoginProjection> buscarLoginsPorPerfilProfessor() {
        return loginRepository.findAllLoginsByPerfilProfessor();
    }

    @Transactional(readOnly = true)
    public Login buscarPorUsername(String username) {
        return loginRepository.findByLogin(username).orElseThrow(
                () -> new LoginEntityNotFoundException(String.format("Usuario '%s' não encontrado", username))
        );
    }

    public String buscarPerfilPorUsername(String username) {
        Login login = buscarPorUsername(username);
        return login.getPerfil().getNome();
    }
}
