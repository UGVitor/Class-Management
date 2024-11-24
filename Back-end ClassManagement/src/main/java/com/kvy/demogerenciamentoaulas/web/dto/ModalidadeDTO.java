package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModalidadeDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome não pode conter caracteres especiais ou números")
    private String nome;
}
