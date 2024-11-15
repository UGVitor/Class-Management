package com.kvy.demogerenciamentoaulas.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class HorarioDTO {

    private Long id;
    private LocalTime horaInicio;
    private LocalTime horaTermino;

}