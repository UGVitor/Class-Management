package com.kvy.demogerenciamentoaulas.serviceTest;

import com.kvy.demogerenciamentoaulas.web.dto.AulaDTO;
import com.kvy.demogerenciamentoaulas.repository.AulaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kvy.demogerenciamentoaulas.service.AulaService;
import static org.mockito.Mockito.when;
import com.kvy.demogerenciamentoaulas.Adapter.AulaAdapter;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository aulaRepository;

    @InjectMocks
    private AulaService AulaService;

    @Test
    void deveSalvarUmaAula() {
    }

    @Test
    void deveBuscarUmaAulaPorId() {

    }

    @Test
    void deveEditarUmaAula() {

    }

    @Test
    void deveExcluirUmaAula() {

    }

    @Test
    void deveBuscarTodasAsAulas() {

    }

    @Test
    void deveBuscarAulasPorDia() {

    }
}