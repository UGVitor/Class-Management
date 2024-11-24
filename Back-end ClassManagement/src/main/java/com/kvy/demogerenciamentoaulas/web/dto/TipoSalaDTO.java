package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSalaDTO {

    private Long id;

    @NotBlank(message = "O campo tipoSala é obrigatório")
    @Size(max = 50, message = "O campo tipoSala não pode ter mais de 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O campo tipoSala não pode conter caracteres especiais ou números")
    private String tipoSala;
}
