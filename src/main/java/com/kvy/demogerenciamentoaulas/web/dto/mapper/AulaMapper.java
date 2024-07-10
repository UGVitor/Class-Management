package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.kvy.demogerenciamentoaulas.web.dto.mapper.LoginMapper.toDto;

public class AulaMapper {


    public static Aula toAula(AulaCreateDto aulaCreateDto){

        return new ModelMapper().map(aulaCreateDto, Aula.class);

    }

    public static AulaResponseDto toAulaDto(Aula aula){

       ModelMapper mapper = new ModelMapper();
       return mapper.map(aula, AulaResponseDto.class);

    }
    public static List<AulaResponseDto> toListDto(List<Aula> aulaList){
        return aulaList.stream().map(aula -> toAulaDto(aula)).collect(Collectors.toList());
    }
}
