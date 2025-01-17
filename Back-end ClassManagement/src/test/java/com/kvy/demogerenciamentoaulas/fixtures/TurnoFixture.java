package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.entity.Turno;

public class TurnoFixture {

    public static Turno fixtureTurnoDTO1() {
        return Turno.builder()
                .id(1L)
                .turno("Matutino")
                .build();
    }

    public static Turno fixtureTurnoDTO2() {
        return Turno.builder()
                .id(2L)
                .turno("Vespertino")
                .build();
    }
}
