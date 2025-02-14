package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.entity.Modalidade;
import com.kvy.demogerenciamentoaulas.exception.CursoEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.CursoRepository;
import com.kvy.demogerenciamentoaulas.repository.Projection.CursoProjection;
import com.kvy.demogerenciamentoaulas.service.CursoService;
import com.kvy.demogerenciamentoaulas.service.ModalidadeService;
import com.kvy.demogerenciamentoaulas.web.dto.CursoDTO;
import com.kvy.demogerenciamentoaulas.fixtures.CursoDTOFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks; import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

class CursoServiceTest {

  @Mock private CursoRepository cursoRepository;

  @Mock
  private ModalidadeService modalidadeService;

  @InjectMocks private CursoService cursoService;

      @Test
      void deveSalvarUmCurso() {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setCurso("ADS");
        cursoDTO.setId(1L);
        cursoDTO.setModalidade(1L);

        Curso cursoMock = new Curso();
        cursoMock.setId(1L);
        cursoMock.setCurso("ADS");
        cursoMock.setModalidade(new Modalidade());

        when(modalidadeService.buscarPorId(anyLong())).thenReturn(new Modalidade());
        when(cursoRepository.save(any())).thenReturn(cursoMock);

        var cursoSalvo = cursoService.salvar(cursoDTO);

        assertNotNull(cursoSalvo, "O curso salvo não deveria ser nulo");
        assertEquals(cursoDTO.getCurso(), cursoSalvo.getCurso(), "O nome do curso salvo deve ser o mesmo do DTO");

        verify(cursoRepository, times(1)).save(any());
      }

      @Test
      void deveBuscarUmCursoPorId() {
        Long cursoId = 1L;

        Curso cursoMock = new Curso();
        cursoMock.setId(cursoId);
        cursoMock.setCurso("ADS");
        cursoMock.setModalidade(new Modalidade());

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(cursoId);
        cursoDTO.setCurso("ADS");
        cursoDTO.setModalidade(1L);

        when(cursoRepository.findById(anyLong())).thenReturn(Optional.of(cursoMock));

        var cursoEncontrado = cursoService.buscarPorId(cursoId);

        assertNotNull(cursoEncontrado, "O curso encontrado não deveria ser nulo");
        assertEquals(cursoDTO.getCurso(), cursoEncontrado.getCurso(), "O nome do curso deve ser igual ao esperado");

        verify(cursoRepository, times(1)).findById(anyLong());
      }

      @Test
      void deveEditarUmCurso() {
        Long cursoId = 1L;

        Curso cursoExistente = new Curso();
        cursoExistente.setId(cursoId);
        cursoExistente.setCurso("ADS");
        cursoExistente.setModalidade(new Modalidade());

        CursoDTO cursoDTOAtualizado = new CursoDTO();
        cursoDTOAtualizado.setId(cursoId);
        cursoDTOAtualizado.setCurso("Engenharia de Software");
        cursoDTOAtualizado.setModalidade(2L);

        Curso cursoEditadoMock = new Curso();
        cursoEditadoMock.setId(cursoId);
        cursoEditadoMock.setCurso(cursoDTOAtualizado.getCurso());
        cursoEditadoMock.setModalidade(new Modalidade());

        when(cursoRepository.findById(anyLong())).thenReturn(Optional.of(cursoExistente));
        when(cursoRepository.save(any())).thenReturn(cursoEditadoMock);

        var cursoEditado = cursoService.editar(cursoId, cursoDTOAtualizado);

        assertNotNull(cursoEditado, "O curso editado não deveria ser nulo");
        assertEquals(cursoDTOAtualizado.getCurso(), cursoEditado.getCurso(), "O nome do curso deve ter sido atualizado");

        verify(cursoRepository, times(1)).findById(anyLong());
        verify(cursoRepository, times(1)).save(any());
      }

      @Test
      void deveExcluirUmCurso() {
        Long cursoId = 1L;

        Curso cursoExistente = new Curso();
        cursoExistente.setId(cursoId);
        cursoExistente.setCurso("ADS");

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(cursoExistente));
        doNothing().when(cursoRepository).delete(any(Curso.class));

        cursoService.excluir(cursoId);

        verify(cursoRepository, times(1)).findById(cursoId);
        verify(cursoRepository, times(1)).delete(any(Curso.class));
      }

      @Test
      void deveTentarExcluirUmCursoComIDInvalido() {
        Long idInvalido = 999L;

        when(cursoRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThrows(CursoEntityNotFoundException.class, () -> cursoService.excluir(idInvalido));

        verify(cursoRepository, times(1)).findById(idInvalido);
        verify(cursoRepository, never()).deleteById(anyLong());
        verify(cursoRepository, never()).delete(any(Curso.class));
      }

      @Test
      void deveTentarBuscarUmCursoComIDInvalido() {
        Long idInvalido = 999L;

        when(cursoRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThrows(CursoEntityNotFoundException.class, () -> cursoService.buscarPorId(idInvalido));

        verify(cursoRepository, times(1)).findById(idInvalido);
      }

      @Test
      void deveTentarSalvarUmCursoComNomeNull() {
        CursoDTO cursoDTO = CursoDTOFixture.fixtureCursoDTONullName();

        assertThrows(IllegalArgumentException.class, () -> cursoService.salvar(cursoDTO));
      }

      @Test
      void deveBuscarTodosOsCursos() {
        CursoProjection curso1 = mock(CursoProjection.class);
        CursoProjection curso2 = mock(CursoProjection.class);

        when(curso1.getId()).thenReturn(1L);
        when(curso1.getCurso()).thenReturn("ADS");
        when(curso2.getId()).thenReturn(2L);
        when(curso2.getCurso()).thenReturn("Ciência da Computação");

        List<CursoProjection> listaCursos = List.of(curso1, curso2);

        when(cursoRepository.findAllCursosWithModalidadeNome()).thenReturn(listaCursos);

        var cursos = cursoService.buscarTodos();

        System.out.println("Cursos encontrados: " + cursos.size());
        assertNotNull(cursos);
        assertEquals(2, cursos.size(), "A lista de cursos deve conter 2 cursos");

        assertEquals(1L, cursos.get(0).getId());
        assertEquals("ADS", cursos.get(0).getCurso());
        assertEquals(2L, cursos.get(1).getId());
        assertEquals("Ciência da Computação", cursos.get(1).getCurso());

        verify(cursoRepository, times(1)).findAllCursosWithModalidadeNome();
      }

      @Test
      void deveBuscarTodosCursosEmUmaTabelaVazia() {
        when(cursoRepository.findAllCursosWithModalidadeNome()).thenReturn(Collections.emptyList());

        List<CursoProjection> cursoEncontrado = cursoService.buscarTodos();

        assertNotNull(cursoEncontrado);
        assertTrue(cursoEncontrado.isEmpty());

        verify(cursoRepository, times(1)).findAllCursosWithModalidadeNome();
      }

        @Test
        void deveTentarBuscarUmCursoComIdInvalido() {
            Long idInvalido = 999L;
            when(cursoRepository.findById(idInvalido)).thenReturn(Optional.empty());

            assertThrows(CursoEntityNotFoundException.class, () -> cursoService.buscarPorId(idInvalido));
            verify(cursoRepository, times(1)).findById(idInvalido);
        }


        @Test
        void deveTentarEditarUmCursoComIdInvalido() {
            Long idInvalido = 999L;

            CursoDTO cursoDTOEditado = CursoDTO.builder()
                    .id(idInvalido)
                    .curso("Engenharia de Software")
                    .modalidade(2L)
                    .build();

            when(cursoRepository.findById(idInvalido)).thenReturn(Optional.empty());

            assertThrows(CursoEntityNotFoundException.class, () -> cursoService.editar(idInvalido, cursoDTOEditado));
            verify(cursoRepository, times(1)).findById(idInvalido);

        }


}
