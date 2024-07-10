package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Disciplina;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.DisciplinaResponseDto;
import org.modelmapper.ModelMapper;

public class DisciplinaMapper {
    public static Disciplina toDisciplina(DisciplinaCreateDto disciplinaCreateDto){

        return new ModelMapper().map(disciplinaCreateDto, Disciplina.class);

    }

    public static DisciplinaResponseDto toDisciplinaDto(Disciplina disciplina){

        ModelMapper mapper = new ModelMapper();
        return mapper.map(disciplina, DisciplinaResponseDto.class);

    }
}
