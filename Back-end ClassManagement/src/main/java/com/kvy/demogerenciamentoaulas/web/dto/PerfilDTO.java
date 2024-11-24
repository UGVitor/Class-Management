package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números")
    private String nome;

}
