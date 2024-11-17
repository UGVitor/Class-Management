package com.kvy.demogerenciamentoaulas.repository.Projection;

public interface SalaProjection {
    Long getId();
    String getSala();
    String getTipoSalaNome();
    int getNumero(); // Novo atributo
    int getCapacidade(); // Novo atributo
}
