package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSenhaDTO {
    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}
