package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.entity.Aula;
import com.kvy.demogerenciamentoaulas.entity.Curso;
import com.kvy.demogerenciamentoaulas.web.dto.CursoCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaCreateDto;
import com.kvy.demogerenciamentoaulas.web.dto.AulaResponseDto;
import com.kvy.demogerenciamentoaulas.web.dto.CursoResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CursoMapper {
    public static Curso toCurso(CursoCreateDto cursoCreateDto){

        return new ModelMapper().map(cursoCreateDto, Curso.class);

    }

    public static CursoResponseDto toCursoDto(Curso curso){

        ModelMapper mapper = new ModelMapper();
        return mapper.map(curso, CursoResponseDto.class);

    }
    public static List<CursoResponseDto> toListDto(List<Curso> cursoList){
        return cursoList.stream().map(curso -> toCursoDto(curso)).collect(Collectors.toList());
    }
}
