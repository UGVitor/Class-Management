package com.kvy.demogerenciamentoaulas.fixtures;


import com.kvy.demogerenciamentoaulas.web.dto.PerfilDTO;

public class PerfilDTOFixture {

    public static PerfilDTO fixturePerfilDTO1() {
        return PerfilDTO.builder()
                .id(1L)
                .id(Long.valueOf("Professor"))
                .build();
    }

    public static PerfilDTO fixturePerfilDTO2() {
        return PerfilDTO.builder()
                .id(2L)
                .id(Long.valueOf("Fiscal de Corredor"))
                .build();
    }

    public static PerfilDTO fixturePerfilDTO3() {
        return PerfilDTO.builder()
                .id(3L)
                .id(Long.valueOf("ADMIN"))
                .build();
    }

    public static PerfilDTO fixturePerfilDTONullName() {
        return PerfilDTO.builder()
                .id(5L)
                .id(null)
                .build();
    }
    
    public static PerfilDTO fixturePerfilDTOEmptyName() {
        return PerfilDTO.builder()
                .id(6L)
                .nome("")
                .build();
    }
    
    public static PerfilDTO fixturePerfilDTOIdInvalido() {
        return PerfilDTO.builder()
                .id(null)
                .id(Long.valueOf(""))
                .build();
    }
}
