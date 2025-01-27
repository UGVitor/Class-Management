package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.PeriodoDTO;

public class PeriodoDTOFixture {

    public static PeriodoDTO fixturePeriodoDTO1() {
        return PeriodoDTO.builder()
                .id(1L)
                .nome("Primeiro")
                .build();
    }

    public static PeriodoDTO fixturePeriodoDTO2() {
        return PeriodoDTO.builder()
                .id(2L)
                .nome("Segundo")
                .build();
    }

    public static PeriodoDTO fixturePeriodoDTONullName() {
        return PeriodoDTO.builder()
                .id(3L)
                .nome(null)
                .build();
    }
    public static PeriodoDTO fixturePeriodoDTOEmptyName() {
        return PeriodoDTO.builder()
                .id(4L)
                .nome("")
                .build();
    }
    public static PeriodoDTO fixturePeriodoDTOIdInvalido() {
        return PeriodoDTO.builder()
                .id(null)
                .nome("Terceiro")
                .build();
    }
}
