package com.kvy.demogerenciamentoaulas.web.dto.mapper;

import com.kvy.demogerenciamentoaulas.web.dto.CursoCreateDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CursoMapper {
    public static com.kvy.demogerenciamentoaulas.entity.Curso toCurso(CursoCreateDto cursoCreateDto){

        return new ModelMapper().map(cursoCreateDto, com.kvy.demogerenciamentoaulas.entity.Curso.class);

    }

    public static Curso toCursoDto(com.kvy.demogerenciamentoaulas.entity.Curso curso){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(curso, Curso.class);
    }
    public static List<Curso> toListDto(List<com.kvy.demogerenciamentoaulas.entity.Curso> cursoList){
        return cursoList.stream().map(curso -> toCursoDto(curso)).collect(Collectors.toList());
    }
}
