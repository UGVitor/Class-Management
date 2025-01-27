package com.kvy.demogerenciamentoaulas.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaSemanaDTO {

    private Long id;

    @NotBlank(message = "O dia é obrigatório")
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s-]+$", message = "O nome não pode conter caracteres especiais, exceto hífens")
    private String dia;

}
