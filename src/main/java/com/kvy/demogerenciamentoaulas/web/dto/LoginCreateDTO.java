package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class LoginCreateDTO {

    @NotBlank
    @Email(message = "Formato do e-mail está inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String login;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

}
