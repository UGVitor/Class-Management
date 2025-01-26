package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSenhaDTO {

    @NotBlank(message = "O campo senha não pode estar vazio.")
    @Size(min = 6, max = 6, message = "A senha deve ter exatamente 6 caracteres.")
    private String senhaAtual;

    @NotBlank(message = "O campo senha não pode estar vazio.")
    @Size(min = 6, max = 6, message = "A senha deve ter exatamente 6 caracteres.")
    private String novaSenha;

    @NotBlank(message = "O campo senha não pode estar vazio.")
    @Size(min = 6, max = 6, message = "A senha deve ter exatamente 6 caracteres.")
    private String confirmaSenha;
}
