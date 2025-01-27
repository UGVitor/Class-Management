package com.kvy.demogerenciamentoaulas.serviceTest;


import com.kvy.demogerenciamentoaulas.repository.SalaRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


    @Nested
    @ExtendWith(MockitoExtension.class)
    class SalaServiceTest {

        @Mock
        private SalaRepository salaRepository;

        @InjectMocks
        private com.kvy.demogerenciamentoaulas.service.SalaService AulaService;

        @Test
        void deveSalvarUmaSala() {
        }

        @Test
        void deveBuscarUmaSalaPorId() {

        }

        @Test
        void deveEditarUmaSala() {

        }

        @Test
        void deveExcluirUmaSala() {

        }

        @Test
        void deveBuscarTodosAsSalas() {

        }

        @Test
        void deveBuscarSalasPorDia() {

        }

        @Test
        void deveTentarSalvarUmaSalaComNomeNull() {

        }

        @Test
        void deveTentarBuscarUmaSalaComIDInvalido() {

        }

        @Test
        void deveTentarEditarUmaSalaComIDInvalido() {

        }
        @Test
        void deveTentarExcluirUmaSalaComID() {

        }
        @Test
        void deveBuscarTodosOsObjetosDaSalaExistentes() {

        }
        @Test
        void deveBuscarTodosOsObjetosDaSalaInexistentes() {

        }

    }




