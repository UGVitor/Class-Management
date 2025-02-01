package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.HorarioDTO;

import java.time.LocalTime;

public class HorarioDTOFixture {
    public static HorarioDTO criarHorarioDTOValido() {
        return new HorarioDTO(
                1L,
                LocalTime.of(8, 0),  // Hora de início: 08:00
                LocalTime.of(9, 0)   // Hora de término: 09:00
        );
    }

    public static HorarioDTO criarHorarioDTOInvalido() {
        return new HorarioDTO(
                2L,
                LocalTime.of(10, 0),
                LocalTime.of(9, 0)
        );
    }

    public static HorarioDTO criarHorarioDTOSemId() {
        return new HorarioDTO(
                null,
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );
    }

    public static HorarioDTO criarHorarioDTOComHoraInicioNula() {
        return new HorarioDTO(
                3L,
                null,
                LocalTime.of(16, 0)
        );
    }

    public static HorarioDTO criarHorarioDTOComHoraTerminoNula() {
        return new HorarioDTO(
                4L,
                LocalTime.of(17, 0),
                null
        );
    }
}
