package com.kvy.demogerenciamentoaulas.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class HorarioDTO {

    private Long id;

    @NotNull(message = "O campo horaInicio é obrigatório")
    private LocalTime horaInicio;

    @NotNull(message = "O campo horaTermino é obrigatório")
    private LocalTime horaTermino;

    public boolean isHorarioValido() {
        if (horaInicio != null && horaTermino != null) {
            return horaInicio.isBefore(horaTermino);
        }
        return true;
    }
}
