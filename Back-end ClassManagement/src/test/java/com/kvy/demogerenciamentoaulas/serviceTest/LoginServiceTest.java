package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.Adapter.LoginAdapter;
import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.entity.Perfil;
import com.kvy.demogerenciamentoaulas.exception.LoginEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.fixtures.LoginDTOFixture;
import com.kvy.demogerenciamentoaulas.repository.LoginRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.LoginProjection;
import com.kvy.demogerenciamentoaulas.service.LoginService;
import com.kvy.demogerenciamentoaulas.service.PerfilService;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.CreateLoginDTO;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginService loginService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PerfilService perfilService;


    @BeforeEach
    void setup() {
        // 🔹 Criando um perfil mock para ser retornado no teste
        Perfil perfilMock = new Perfil();
        perfilMock.setId(1L);
        perfilMock.setNome("ADMIN");
    }
    @Test
    void deveSalvarUmLoginValido() {
        // 🔹 Criando um DTO de entrada
        CreateLoginDTO createLoginDTO = new CreateLoginDTO("usuarioTeste", "senha123", 1L);

        // 🔹 Criando e inicializando o perfil mock
        Perfil perfilMock = new Perfil();
        perfilMock.setId(1L);
        perfilMock.setNome("ADMIN");

        // 🔹 Mockando a busca do perfil para evitar NullPointerException
        Mockito.when(perfilService.buscarPorId(1L)).thenReturn(perfilMock);

        // 🔹 Mockando a criptografia da senha
        Mockito.when(passwordEncoder.encode("senha123")).thenReturn("senhaCriptografada");

        // 🔹 Mockando o comportamento do repositório ao salvar um Login
        Login loginMock = new Login();
        loginMock.setId(1L);
        loginMock.setLogin("usuarioTeste");
        loginMock.setPassword("senhaCriptografada");
        loginMock.setPerfil(perfilMock);

        Mockito.when(loginRepository.save(any(Login.class))).thenReturn(loginMock);

        // 🔹 Convertendo o DTO para entidade e salvando
        Login loginSalvo = loginService.salvar(loginService.convertToEntityCreate(createLoginDTO));

        // 🔹 Verificações
        assertNotNull(loginSalvo);
        assertEquals("usuarioTeste", loginSalvo.getLogin());
        assertEquals("senhaCriptografada", loginSalvo.getPassword());
        assertEquals(1L, loginSalvo.getPerfil().getId());
    }


    @Test
    void deveBuscaroLoginPorId() {
        Long loginId = 1L;
        LoginDTO loginDTO =  LoginDTOFixture.fixtureLoginDTO1();
        Login login =  LoginAdapter.toEntity(loginDTO);

        when(loginRepository.findById(loginId)).thenReturn(Optional.of(login));

        Login loginEncontrado = loginService.buscarPorId(loginId);

        assertNotNull(loginEncontrado);
        assertEquals(loginId, loginEncontrado.getId());
    }

    @Test
    void deveTentarBuscaroLoginPorIdInexistenteeRetornarLoginoEntityNotFoundException() {
        Long loginId = 1L;

        assertThrows( LoginEntityNotFoundException.class, () ->
        {loginService.buscarPorId(loginId);});
    }



    @Test
    void deveTentarEditarUmLoginComIdInvalidoeRetornarIllegalArgumentException() {
        LoginDTO loginDTO =  LoginDTOFixture.fixtureLoginDTOIdInvalido();

        assertThrows(IllegalArgumentException.class, () ->
        {loginService.editar(loginDTO.getId(), loginDTO);});
    }

    @Test
    void deveTentarEditarUmLoginComNomeNulleRetornarIllegalArgumentException(){
        Long loginId = 1L;
        LoginDTO loginDTO =  LoginDTOFixture.fixtureLoginDTONullName();

        assertThrows(IllegalArgumentException.class, () ->
        {loginService.editar(loginId, loginDTO);});

    }

    @Test
    void deveTentarEditarUmLoginComNomeVazioeRetornarIllegalArgumentException() {
        Long loginId = 1L;
        LoginDTO loginDTO = LoginDTOFixture.fixtureLoginDTOEmptyName();

        assertThrows(IllegalArgumentException.class, () ->
        {loginService.editar(loginId, loginDTO);});
    }


    @Test
    void deveExcluirUmLoginPorId() {

        Long loginId = 1L;
        LoginDTO loginDTO =  LoginDTOFixture.fixtureLoginDTO1();
        Login login =  LoginAdapter.toEntity(loginDTO);

        when(loginRepository.findById(loginId)).thenReturn(Optional.of(login));

        loginService.excluir(loginId);

        verify(loginRepository, times(1)).delete(login);
    }

    @Test
    void deveTentarExcluirUmLoginComIdInvalidoeRetornarRuntimeException() {
        LoginDTO loginDTO = LoginDTOFixture.fixtureLoginDTOIdInvalido();
        assertThrows(RuntimeException.class, () ->
        {loginService.excluir(loginDTO.getId());});

    }

    @Test
    void deveBuscarTodosOsLoginsExistentes() {
        LoginDTO loginDTO1 = LoginDTOFixture.fixtureLoginDTO1();
        LoginDTO loginDTO2 = LoginDTOFixture.fixtureLoginDTO2();
        Login login1 = LoginAdapter.toEntity(loginDTO1);
        Login login2 = LoginAdapter.toEntity(loginDTO2);

        // Simule a projeção
        LoginProjection projection1 = mock(LoginProjection.class);
        LoginProjection projection2 = mock(LoginProjection.class);

        when(loginRepository.findAllLoginsWithPerfilNome()).thenReturn(Arrays.asList(projection1, projection2));

        List<LoginProjection> logins = loginService.buscarTodos();

        assertNotNull(logins);
        assertEquals(2, logins.size());
    }

    @Test
    void deveBuscarTodosOsLoginsEmUmaTabelaVazia() {
        when(loginRepository.findAllLoginsWithPerfilNome()).thenReturn(Collections.emptyList());
        List<LoginProjection> loginsEncontrados = loginService.buscarTodos();
        assertNotNull(loginsEncontrados);
        assertTrue(loginsEncontrados.isEmpty());
    }
}
