package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.entity.*;
import com.kvy.demogerenciamentoaulas.exception.AulaEntityNotFoundException;
import com.kvy.demogerenciamentoaulas.repository.*;
import com.kvy.demogerenciamentoaulas.repository.Projection.AulaProjection;
import com.kvy.demogerenciamentoaulas.service.*;
import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
import com.kvy.demogerenciamentoaulas.Adapter.AulaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;
    @Mock
    private HorarioRepository horarioRepository;
    @Mock
    private SalaRepository salaRepository;
    @Mock
    private TurmaRepository turmaRepository;
    @Mock
    private DiaSemanaRepository diaSemanaRepository;
    @Mock
    private AulaRepository aulaRepository;

    @InjectMocks
    private AulaService aulaService;
    @Mock
    private SalaService salaService;
    @Mock
    private DisciplinaService disciplinaService;
    @Mock
    private HorarioService horarioService;
    @Mock
    private TurmaService turmaService;
    @Mock
    private DiaSemanaService diaSemanaService;

    private AulaDTO aulaDTO;

    @BeforeEach
    void setUp() {
        aulaDTO = AulaDTO.builder()
                .id(1L)
                .disciplinaId(1L)
                .horarioId(1L)
                .salaId(1L)
                .diaSemanaId(1L)
                .turmaId(1L)
                .build();
    }

    @Test
    void deveSalvarUmaAula() {
        // Mock dos objetos necessários
        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);
        when(disciplinaService.buscarPorId(aulaDTO.getDisciplinaId())).thenReturn(disciplina);

        Horario horario = new Horario();
        horario.setId(1L);
        when(horarioService.buscarPorId(aulaDTO.getHorarioId())).thenReturn(horario);

        Sala sala = new Sala();
        sala.setId(1L);
        when(salaService.buscarPorId(aulaDTO.getSalaId())).thenReturn(sala);

        Turma turma = new Turma();
        turma.setId(1L);
        when(turmaService.buscarPorId(aulaDTO.getTurmaId())).thenReturn(turma);

        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setId(1L);
        when(diaSemanaService.buscarPorId(aulaDTO.getDiaSemanaId())).thenReturn(diaSemana);

        // Simulação do salvamento da aula com atribuição de ID
        when(aulaRepository.save(any(Aula.class))).thenAnswer(invocation -> {
            Aula aula = invocation.getArgument(0);
            aula.setId(1L); // Simula a geração do ID no banco
            return aula;
        });

        // Execução do método a ser testado
        Aula aulaSalva = aulaService.salvar(aulaDTO);

        // Asserções para verificar se a aula foi salva corretamente
        assertNotNull(aulaSalva);
        assertEquals(aulaDTO.getDisciplinaId(), aulaSalva.getDisciplina().getId());
        assertEquals(aulaDTO.getHorarioId(), aulaSalva.getHorario().getId());
        assertEquals(aulaDTO.getSalaId(), aulaSalva.getSala().getId());
        assertEquals(aulaDTO.getTurmaId(), aulaSalva.getTurma().getId());
        assertEquals(aulaDTO.getDiaSemanaId(), aulaSalva.getDiaSemana().getId());

        verify(disciplinaService, times(1)).buscarPorId(aulaDTO.getDisciplinaId());
        verify(horarioService, times(1)).buscarPorId(aulaDTO.getHorarioId());
        verify(salaService, times(1)).buscarPorId(aulaDTO.getSalaId());
        verify(turmaService, times(1)).buscarPorId(aulaDTO.getTurmaId());
        verify(diaSemanaService, times(1)).buscarPorId(aulaDTO.getDiaSemanaId());
        verify(aulaRepository, times(1)).save(any(Aula.class));
    }


    @Test
    void deveBuscarUmaAulaPorId() {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(aulaDTO.getDisciplinaId());

        Horario horario = new Horario();
        horario.setId(aulaDTO.getHorarioId());

        Sala sala = new Sala();
        sala.setId(aulaDTO.getSalaId());

        Turma turma = new Turma();
        turma.setId(aulaDTO.getTurmaId());

        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setId(aulaDTO.getDiaSemanaId());

        Aula aula = AulaAdapter.toEntity(aulaDTO);
        aula.setId(aulaDTO.getId());
        aula.setDisciplina(disciplina);
        aula.setHorario(horario);
        aula.setSala(sala);
        aula.setTurma(turma);
        aula.setDiaSemana(diaSemana);

        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));

        Aula resultado = aulaService.buscarPorId(1L);

        assertNotNull(resultado, "Aula retornada pelo serviço é nula");
        assertEquals(1L, resultado.getId(), "ID da aula retornada está incorreto");
        assertEquals(1L, resultado.getDisciplina().getId(), "ID da disciplina da aula retornada está incorreto");
    }



    @Test
    void deveEditarUmaAula() {
        Long aulaId = 1L;

        Aula aula = AulaAdapter.toEntity(aulaDTO);
        aula.setId(aulaId);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(2L);
        when(disciplinaService.buscarPorId(2L)).thenReturn(disciplina);

        Horario horario = new Horario();
        horario.setId(aulaDTO.getHorarioId());
        when(horarioService.buscarPorId(aulaDTO.getHorarioId())).thenReturn(horario);

        Sala sala = new Sala();
        sala.setId(aulaDTO.getSalaId());
        when(salaService.buscarPorId(aulaDTO.getSalaId())).thenReturn(sala);

        Turma turma = new Turma();
        turma.setId(aulaDTO.getTurmaId());
        when(turmaService.buscarPorId(aulaDTO.getTurmaId())).thenReturn(turma);

        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setId(aulaDTO.getDiaSemanaId());
        when(diaSemanaService.buscarPorId(aulaDTO.getDiaSemanaId())).thenReturn(diaSemana);


        when(aulaRepository.findById(aulaId)).thenReturn(Optional.of(aula));
        when(aulaRepository.save(any(Aula.class))).thenReturn(aula);

        aulaDTO.setDisciplinaId(2L);
        Aula aulaEditada = aulaService.editar(aulaId, aulaDTO);

        ArgumentCaptor<Aula> captor = ArgumentCaptor.forClass(Aula.class);
        verify(aulaRepository).save(captor.capture());

        Aula aulaSalva = captor.getValue();
        assertEquals(2L, aulaSalva.getDisciplina().getId());
        verify(aulaRepository, times(1)).findById(aulaId);
        verify(aulaRepository, times(1)).save(any(Aula.class));
    }



    @Test
    void deveExcluirUmaAula() {
        Long aulaId = 1L;
        Aula aula = new Aula();
        aula.setId(aulaId);

        when(aulaRepository.findById(aulaId)).thenReturn(Optional.of(aula));
        doNothing().when(aulaRepository).delete(aula);

        assertDoesNotThrow(() -> aulaService.excluir(aulaId));

        verify(aulaRepository, times(1)).findById(aulaId);
        verify(aulaRepository, times(1)).delete(aula);
    }

    @Test
    void deveBuscarTodasAsAulas() {
        List<AulaProjection> resultado = aulaService.buscarTodos();


    }

    @Test
    void deveLancarExcecaoQuandoAulaNaoEncontrada() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AulaEntityNotFoundException.class, () -> aulaService.buscarPorId(1L));
    }
    @Test
    void deveLancarExcecaoQuandoDisciplinaIdEhNulo() {
        aulaDTO.setDisciplinaId(null);

        assertThrows(IllegalArgumentException.class, () -> aulaService.salvar(aulaDTO));
    }
}
