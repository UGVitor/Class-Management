package com.kvy.demogerenciamentoaulas.repository.Projection;



public interface TurmaProjection {
    Long getId();
    String getNome();
    PeriodoProjection getPeriodo();
    TurnoProjection getTurno();
    CursoProjection getCurso();
    SemestreProjection getSemestre();

    interface PeriodoProjection {
        Long getId();
        String getNome();
    }

    interface TurnoProjection {
        Long getId();
        String getNome();
    }

    interface CursoProjection {
        Long getId();
        String getNome();
    }

    interface SemestreProjection {
        Long getId();
        String getNome();
    }
}

