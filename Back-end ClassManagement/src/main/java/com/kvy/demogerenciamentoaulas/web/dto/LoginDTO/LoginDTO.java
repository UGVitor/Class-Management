package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDTO {

    @NotNull(message = "O campo id não pode estar vazio.")
    private Long id;

    @NotBlank(message = "O campo login não pode estar vazio.")
    private String login;

    @NotNull(message = "O campo perfil não pode estar vazio.")
    private Long perfil;
}
