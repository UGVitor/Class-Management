package com.kvy.demogerenciamentoaulas.fixtures;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaDTO;


public class TurmaDTOFixture {
    public static TurmaDTO fixtureTurmaDTO1() {
        return TurmaDTO.builder()
                .id(1L)
                .nome("ADS5")
                .periodo(1L)
                .semestre(1L)
                .curso(1L)
                .turno(1L)
                .build();
    }

    public static TurmaDTO fixtureTurmaDTO2() {
        return TurmaDTO.builder()
                .id(2L)
                .nome("ADS4")
                .build();
    }

    public static TurmaDTO fixtureTurmaDTO3() {
        return TurmaDTO.builder()
                .id(3L)
                .nome("ADS2")
                .build();
    }

    public static TurmaDTO fixtureTurmaDTO4() {
        return TurmaDTO.builder()
                .id(4L)
                .nome("ADS4N")
                .build();
    }
    public static TurmaDTO fixtureTurmaDTONullName() {
        return TurmaDTO.builder()
                .id(5L)
                .nome(null)
                .build();
    }
    public static TurmaDTO fixtureTurmaDTOEmptyName() {
        return TurmaDTO.builder()
                .id(6L)
                .nome("")
                .build();
    }
    public static TurmaDTO fixtureTurmaDTOIdInvalido() {
        return TurmaDTO.builder()
                .id(null)
                .nome("")
                .build();
    }
}