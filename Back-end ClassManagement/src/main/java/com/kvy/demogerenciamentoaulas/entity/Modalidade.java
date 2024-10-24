package com.kvy.demogerenciamentoaulas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @Data @Entity
@Table(name = "Modalidade")
public class Modalidade implements Serializable {

    @Id
    private Long id;



}
