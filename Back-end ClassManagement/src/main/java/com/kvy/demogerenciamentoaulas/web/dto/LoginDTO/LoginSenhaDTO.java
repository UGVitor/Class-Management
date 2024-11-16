package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSenhaDTO {
    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}
