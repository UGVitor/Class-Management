package com.kvy.demogerenciamentoaulas.fixtures;
import com.kvy.demogerenciamentoaulas.web.dto.SalaDTO;


public class SalaDTOFixture {

        public static SalaDTO fixtureSalaDTO1() {
            return SalaDTO.builder()
                    .id(1L)
                    .numero(1)
                    .build();
        }

        public static SalaDTO fixtureSalaDTO2() {
            return SalaDTO.builder()
                    .id(2L)
                    .numero(50)
                    .build();
        }

        public static SalaDTO fixtureSalaDTONullName() {
            return SalaDTO.builder()
                    .id(5L)
                    .tipoSala(null)
                    .build();
        }
        public static SalaDTO fixtureSalaDTOEmptyName() {
            return SalaDTO.builder()
                    .id(6L)
                    .tipoSala(Long.valueOf(""))
                    .build();
        }
        public static SalaDTO fixtureSalaDTOIdInvalido() {
            return SalaDTO.builder()
                    .id(null)
                    .build();
        }
    }


