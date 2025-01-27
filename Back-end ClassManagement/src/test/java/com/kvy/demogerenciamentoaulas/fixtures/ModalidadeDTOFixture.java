package com.kvy.demogerenciamentoaulas.fixtures;
import com.kvy.demogerenciamentoaulas.web.dto.ModalidadeDTO;

public class ModalidadeDTOFixture {
    public static ModalidadeDTO fixtureModalidadeDTO1() {
        return ModalidadeDTO.builder()
                .id(1L)
                .nome("Superior")
                .build();
    }

    public static ModalidadeDTO fixtureModalidadeDTO2() {
        return ModalidadeDTO.builder()
                .id(2L)
                .nome("Subsequente")
                .build();
    }

    public static ModalidadeDTO fixtureModalidadeDTONullName() {
        return ModalidadeDTO.builder()
                .id(5L)
                .nome(null)
                .build();
    }
    public static ModalidadeDTO fixtureModalidadeDTOEmptyName() {
        return ModalidadeDTO.builder()
                .id(6L)
                .nome("")
                .build();
    }
    public static ModalidadeDTO fixtureModalidadeDTOIdInvalido() {
        return ModalidadeDTO.builder()
                .id(null)
                .nome("")
                .build();
    }
}
