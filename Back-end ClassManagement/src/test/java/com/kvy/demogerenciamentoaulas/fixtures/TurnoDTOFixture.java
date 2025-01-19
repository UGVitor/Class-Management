package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.TurnoDTO;

public class TurnoDTOFixture {

    public static TurnoDTO fixtureTurnoDTO1() {
        return TurnoDTO.builder()
                .id(1L)
                .turno("Matutino")
                .build();
    }

    public static TurnoDTO fixtureTurnoDTO2() {
        return TurnoDTO.builder()
                .id(2L)
                .turno("Vespertino")
                .build();
    }

    public static TurnoDTO fixtureTurnoDTO3() {
        return TurnoDTO.builder()
                .id(3L)
                .turno("Noturno")
                .build();
    }

    public static TurnoDTO fixtureTurnoDTO4() {
        return TurnoDTO.builder()
                .id(4L)
                .turno("Integral")
                .build();
    }
}