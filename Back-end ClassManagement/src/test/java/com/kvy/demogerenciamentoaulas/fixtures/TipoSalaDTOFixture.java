package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;


public class TipoSalaDTOFixture {

    public static TipoSalaDTO fixtureTipoSalaDTO1() {
        return TipoSalaDTO.builder()
                .id(1L)
                .tipoSala("Sala de Aula")
                .build();
    }

    public static TipoSalaDTO fixtureTipoSalaDTO2() {
        return TipoSalaDTO.builder()
                .id(2L)
                .tipoSala("Laboratorio")
                .build();
    }

    public static TipoSalaDTO fixtureTipoSalaDTONullName() {
        return TipoSalaDTO.builder()
                .id(5L)
                .tipoSala(null)
                .build();
    }
    public static TipoSalaDTO fixtureTipoSalaDTOEmptyName() {
        return TipoSalaDTO.builder()
                .id(1L)
                .tipoSala("")
                .build();
    }
    public static TipoSalaDTO fixtureTipoSalaDTOIdInvalido() {
        return TipoSalaDTO.builder()
                .id(null)
                .tipoSala("")
                .build();
    }
}
