package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponseDto {

    private Long id;
    private String login;
    private String role;
    private String password;

}
