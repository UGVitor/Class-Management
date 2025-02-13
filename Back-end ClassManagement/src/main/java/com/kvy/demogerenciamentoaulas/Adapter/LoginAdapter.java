package com.kvy.demogerenciamentoaulas.Adapter;

import com.kvy.demogerenciamentoaulas.entity.Login;
import com.kvy.demogerenciamentoaulas.web.dto.LoginDTO.LoginDTO;


public class LoginAdapter {
    public static Login toEntity(LoginDTO dto) {
        return Login.builder()
                .id(dto.getId())
                .login(dto.getLogin())
                .build();
    }

    public static LoginDTO toDTO(Login login) {
        return LoginDTO.builder()
                .id(login.getId())
                .login(login.getLogin())
                .build();
    }
}
