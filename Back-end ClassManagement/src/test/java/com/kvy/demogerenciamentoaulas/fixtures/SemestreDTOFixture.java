package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.SemestreDTO;
import com.kvy.demogerenciamentoaulas.web.dto.TipoSalaDTO;

public class SemestreDTOFixture {
    public static SemestreDTO fixtureSemestreDTO1() {
        return SemestreDTO.builder()
                .id(1L)
                .semestre("Primeiro")
                .build();
    }

    public static SemestreDTO fixtureSemestreDTO2() {
        return SemestreDTO.builder()
                .id(2L)
                .semestre("Segundo")
                .build();
    }

    public static SemestreDTO fixtureSemestreDTONullName() {
        return SemestreDTO.builder()
                .id(5L)
                .semestre(null)
                .build();
    }
    public static SemestreDTO fixtureSemestreDTOEmptyName() {
        return SemestreDTO.builder()
                .id(6L)
                .semestre("")
                .build();
    }
    public static SemestreDTO fixtureSemestreDTOIdInvalido() {
        return SemestreDTO.builder()
                .id(null)
                .semestre("")
                .build();
    }
}
