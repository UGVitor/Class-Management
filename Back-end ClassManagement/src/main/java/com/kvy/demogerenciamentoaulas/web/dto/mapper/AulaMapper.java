package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.TurmaResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AulaMapper {


    public static Aula toAula(AulaCreateDto aulaCreateDto){

        return new ModelMapper().map(aulaCreateDto, Aula.class);

    }

    public static TurmaResponseDto toAulaDto(Aula aula){

       ModelMapper mapper = new ModelMapper();
       return mapper.map(aula, TurmaResponseDto.class);

    }
    public static List<TurmaResponseDto> toListDto(List<Aula> aulaList){
        return aulaList.stream().map(aula -> toAulaDto(aula)).collect(Collectors.toList());
    }
}
