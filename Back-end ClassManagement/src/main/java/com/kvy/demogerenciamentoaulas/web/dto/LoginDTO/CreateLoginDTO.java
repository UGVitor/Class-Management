package com.kvy.demogerenciamentoaulas.web.dto.LoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoginDTO {
    private String login;
    private String password;
    private Long perfilId;
}