package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {

    private Long id;

    @NotBlank(message = "O campo sala é obrigatório")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s]+$", message = "O campo sala só pode conter letras, números e espaços")
    private String sala;

    @Setter
    @Getter
    @NotNull(message = "O campo tipoSala é obrigatório")
    @Positive(message = "O campo tipoSala deve ser um valor positivo")
    private Long tipoSala;

    @Positive(message = "O campo numero deve ser um valor positivo")
    @Min(value = 1, message = "O número deve ser pelo menos 1")
    @Max(value = 20, message = "O número não pode exceder 999")
    private int numero;

    @Positive(message = "O campo capacidade deve ser um valor positivo")
    @Min(value = 1, message = "A capacidade deve ser pelo menos 1")
    @Max(value = 50, message = "A capacidade não pode exceder 500")
    private int capacidade;

}
