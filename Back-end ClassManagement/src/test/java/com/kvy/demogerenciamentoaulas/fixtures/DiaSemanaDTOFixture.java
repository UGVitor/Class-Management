package com.kvy.demogerenciamentoaulas.fixtures;
import com.kvy.demogerenciamentoaulas.web.dto.DiaSemanaDTO;

public class DiaSemanaDTOFixture {

        public static DiaSemanaDTO fixtureDiaSemanaDTO1() {
            return DiaSemanaDTO.builder()
                    .id(1L)
                    .dia("Segunda-Feira")
                    .build();
        }

        public static DiaSemanaDTO fixtureDiaSemanaDTO2() {
            return DiaSemanaDTO.builder()
                    .id(2L)
                    .dia("Terça-Feira")
                    .build();
        }

        public static DiaSemanaDTO fixtureDiaSemanaDTONullName() {
            return DiaSemanaDTO.builder()
                    .id(5L)
                    .dia(null)
                    .build();
        }
        public static DiaSemanaDTO fixtureDiaSemanaDTOEmptyName() {
            return DiaSemanaDTO.builder()
                    .id(6L)
                    .dia("")
                    .build();
        }
        public static DiaSemanaDTO fixtureDiaSemanaDTOIdInvalido() {
            return DiaSemanaDTO.builder()
                    .id(null)
                    .dia("")
                    .build();
        }
    }


