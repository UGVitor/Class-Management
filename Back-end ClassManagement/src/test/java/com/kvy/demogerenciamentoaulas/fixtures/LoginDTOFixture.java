package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginDTO;


public class LoginDTOFixture {
    public static LoginDTO fixtureLoginDTO1() {
        return LoginDTO.builder()
                .id(1L)
                .login("Matutino")
                .perfil(1L) // Adicione o ID do perfil
                .build();
    }

    public static LoginDTO fixtureLoginDTO2() {
        return LoginDTO.builder()
                .id(2L)
                .login("Vespertino")
                .perfil(2L) // Adicione o ID do perfil
                .build();
    }

    public static LoginDTO fixtureLoginDTO3() {
        return LoginDTO.builder()
                .id(3L)
                .login("Noturno")
                .perfil(3L) // Adicione o ID do perfil
                .build();
    }

    public static LoginDTO fixtureLoginDTO4() {
        return LoginDTO.builder()
                .id(4L)
                .login("Integral")
                .perfil(4L) // Adicione o ID do perfil
                .build();
    }
    public static LoginDTO fixtureLoginDTONullName() {
        return LoginDTO.builder()
                .id(5L)
                .login(null)
                .perfil(1L) // Adicione o ID do perfil
                .build();
    }
    public static LoginDTO fixtureLoginDTOEmptyName() {
        return LoginDTO.builder()
                .id(6L)
                .login("")
                .perfil(1L) // Adicione o ID do perfil
                .build();
    }
    public static LoginDTO fixtureLoginDTOIdInvalido() {
        return LoginDTO.builder()
                .id(null)
                .login("")
                .perfil(1L) // Adicione o ID do perfil
                .build();
    }

}
