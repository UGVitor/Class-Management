package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;

public class SalaDTOFixture {

    public static SalaDTO fixtureSalaDTO1() {
        return SalaDTO.builder()
                .id(1L)
                .tipoSala(1L)
                .numero(1)
                .capacidade(30)
                .build();
    }

    public static SalaDTO fixtureSalaDTO2() {
        return SalaDTO.builder()
                .id(2L)
                .tipoSala(2L)
                .numero(50)
                .capacidade(50)
                .build();
    }

    public static SalaDTO fixtureSalaDTONullTipoSala() {
        return SalaDTO.builder()
                .id(5L)
                .tipoSala(null)
                .numero(10)
                .capacidade(20)
                .build();
    }

    public static SalaDTO fixtureSalaDTOEmptyTipoSala() {
        return SalaDTO.builder()
                .id(6L)
                .tipoSala(null)
                .numero(15)
                .capacidade(25)
                .build();
    }

    public static SalaDTO fixtureSalaDTOIdInvalido() {
        return SalaDTO.builder()
                .id(null)
                .tipoSala(3L)
                .numero(5)
                .capacidade(15)
                .build();
    }

    public static SalaDTO fixtureLaboratorio() {
        return SalaDTO.builder()
                .id(7L)
                .tipoSala(4L)
                .numero(20)
                .capacidade(40)
                .build();
    }
}
