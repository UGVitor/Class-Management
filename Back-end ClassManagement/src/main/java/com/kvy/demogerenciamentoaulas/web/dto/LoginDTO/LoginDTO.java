package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    private Long id;
    private String login;
    private Long perfil;
}
