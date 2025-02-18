package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaDTO {

    private Long id;

    @Setter
    @Getter
    @NotNull(message = "O campo tipoSala é obrigatório")
    @Positive(message = "O campo tipoSala deve ser um valor positivo")
    private Long tipoSala;

    @Positive(message = "O campo numero deve ser um valor positivo")
    @Min(value = 1, message = "O número deve ser pelo menos 1")
    @Max(value = 99, message = "O número não pode exceder 99")
    @NotNull(message = "O campo número é obrigatório")
    private Integer numero;

    @Positive(message = "O campo capacidade deve ser um valor positivo")
    @Min(value = 1, message = "A capacidade deve ser pelo menos 1")
    @Max(value = 999, message = "A capacidade não pode exceder 999")
    @NotNull(message = "O campo capacidade é obrigatório")
    private Integer capacidade;


    public static void setsalaId(Object o) {
    }

    public static Long getSalaId() {
        return Long.valueOf(0);
    }

    public Long getTipoSalaId() {
        return null;
    }

    public Object getId(long l) {
        return null;
    }
}
