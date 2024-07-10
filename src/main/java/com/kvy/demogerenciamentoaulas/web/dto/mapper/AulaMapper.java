package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import org.modelmapper.ModelMapper;

public class AulaMapper {


    public static Aula toAula(AulaCreateDto aulaCreateDto){

        return new ModelMapper().map(aulaCreateDto, Aula.class);

    }

    public static AulaResponseDto toAulaDto(Aula aula){

       ModelMapper mapper = new ModelMapper();
       return mapper.map(aula, AulaResponseDto.class);

    }
}
