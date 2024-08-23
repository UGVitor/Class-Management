package com.kvy.demogerenciamentoaulas.web.dto;

import com.kvy.demogerenciamentoaulas.entity.Login; // Importando a enumeração diretamente
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class LoginCreateDTO {

    @NotBlank
    @Email(message = "Formato do e-mail está inválido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String login;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    @NotNull(message = "O campo 'role' não pode ser nulo")
    private Login.Role role;

}
