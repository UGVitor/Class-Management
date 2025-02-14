package com.kvy.demogerenciamentoaulas.fixtures;

import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;

public class AulaDTOFixture
{
        public static AulaDTO fixtureAulaDTO1() {
            return AulaDTO.builder()
                    .id(1L)
                    .disciplinaId(1L)
                    .horarioId(1L)
                    .salaId(1L)
                    .turmaId(1L)
                    .diaSemanaId(1L)
                    .build();
        }

        public static AulaDTO fixtureAulaDTO2() {
            return AulaDTO.builder()
                    .id(2L)
                    .disciplinaId(2L)
                    .horarioId(2L)
                    .salaId(2L)
                    .turmaId(2L)
                    .diaSemanaId(2L)
                    .build();
        }

        public static AulaDTO fixtureAulaDTO3() {
            return AulaDTO.builder()
                    .id(3L)
                    .disciplinaId(3L)
                    .horarioId(3L)
                    .salaId(3L)
                    .turmaId(3L)
                    .diaSemanaId(3L)
                    .build();
        }

        public static AulaDTO fixtureAulaDTO4() {
            return AulaDTO.builder()
                    .id(4L)
                    .disciplinaId(4L)
                    .horarioId(4L)
                    .salaId(4L)
                    .turmaId(4L)
                    .diaSemanaId(5L)
                    .build();
        }
        public static AulaDTO fixtureAulaDTONullName() {
            return AulaDTO.builder()
                    .id(5L)
                    .disciplinaId(5L)
                    .horarioId(5L)
                    .salaId(5L)
                    .turmaId(5L)
                    .diaSemanaId(5L)
                    .build();
        }
        public static AulaDTO fixtureAulaDTOEmptyName() {
            return AulaDTO.builder()
                    .id(6L)
                    .disciplinaId(6L)
                    .horarioId(6L)
                    .salaId(6L)
                    .turmaId(6L)
                    .diaSemanaId(6L)
                    .build();
        }
        public static AulaDTO fixtureAulaDTOIdInvalido() {
            return AulaDTO.builder()
                    .id(null)
                    .build();
        }
    }