package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginAuthenticateDTO {

    @NotBlank(message = "O campo login não pode estar vazio.")
    private String login;

    @NotBlank(message = "O campo senha não pode estar vazio.")
    @Size(min = 6, max = 6, message = "A senha deve ter exatamente 6 caracteres.")
    private String password;
}