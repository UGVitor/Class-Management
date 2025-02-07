package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.repository.CursoRepository;
import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kvy.demogerenciamentoaulas.fixtures.CursoDTOFixture;

import static org.junit.Assert.assertThrows;


@Nested
    @ExtendWith(MockitoExtension.class)
    class CursoServiceTest {

        @Mock
        private CursoRepository cursoRepository;

        @InjectMocks
        private com.kvy.demogerenciamentoaulas.service.CursoService cursoService;

        @Test
        void deveSalvarUmCurso() {

        }


        @Test
        void deveBuscarUmCursoPorId() {

        }

        @Test
        void deveEditarUmCurso() {

        }

        @Test
        void deveExcluirUmCurso() {

        }

        @Test
        void deveBuscarTodosOsCursos() {

        }

        @Test
        void deveBuscarCursoPorDia() {

        }

        @Test
        void deveTentarSalvarUmCursoComNomeNull() {

        }

        @Test
        void deveTentarBuscarUmCursoComIDInvalido() {

        }

        @Test
        void deveTentarEditarUmCursoComIDInvalido() {

        }
        @Test
        void deveTentarExcluirUmCursoComID() {

        }
        @Test
        void deveBuscarTodosOsObjetosDoCursoExistentes() {

        }
        @Test
        void deveBuscarTodosOsObjetosDoCursoInexistentes() {

        }

    }

