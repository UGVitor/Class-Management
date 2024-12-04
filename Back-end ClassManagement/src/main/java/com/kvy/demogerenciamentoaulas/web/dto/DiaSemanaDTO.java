package com.kvy.demogerenciamentoaulas.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaSemanaDTO {

    private Long id;

    @NotBlank(message = "O dia é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s-]+$", message = "O nome não pode conter caracteres especiais, exceto hífens, ou números")
    private String dia;

}
