package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.repository.PerfilRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


    @Nested
    @ExtendWith(MockitoExtension.class)
    class PerfilServiceTest {

        @Mock
        private PerfilRepository perfilRepository;

        @InjectMocks
        private com.kvy.demogerenciamentoaulas.service.PerfilService PerfilService;

        @Test
        void deveSalvarUmPerfil() {
        }

        @Test
        void deveBuscarUmPerfilPorId() {

        }

        @Test
        void deveEditarUmPerfil() {

        }

        @Test
        void deveExcluirUmPerfil() {

        }

        @Test
        void deveBuscarTodosOsPerfis() {

        }

        @Test
        void deveBuscarPerfilPorDia() {

        }

        @Test
        void deveTentarSalvarUmPerfilComNomeNull() {

        }

        @Test
        void deveTentarBuscarUmPerfilComIDInvalido() {

        }

        @Test
        void deveTentarEditarUmPerfilComIDInvalido() {

        }

        @Test
        void deveTentarExcluirUmPerfilComID() {

        }

        @Test
        void deveBuscarTodosOsObjetosDoPerfilExistentes() {

        }

        @Test
        void deveBuscarTodosOsObjetosDoPerfilInexistentes() {

        }

    }

