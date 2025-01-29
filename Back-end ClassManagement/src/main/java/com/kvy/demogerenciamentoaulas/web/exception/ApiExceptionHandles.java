package com.kvy.demogerenciamentoaulas.web.exception;

import com.kvy.demogerenciamentoaulas.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @Slf4j
public class ApiExceptionHandles {

    @ExceptionHandler(PeriodoUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> periodoUniqueViolationException(PeriodoUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(PeriodoEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> periodoEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(DiaSemanaUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> diaSemanaUniqueViolationException(DiaSemanaUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(DiaSemanaEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> diaSemanaEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(SemestreUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> semestreUniqueViolationException(SemestreUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(SemestreEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> semestreEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(TipoSalaUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> tipoSalaUniqueViolationException(TipoSalaUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(TipoSalaEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> tipoSalaEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(LoginUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> loginUniqueViolationException(LoginUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(LoginEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> loginEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(DisciplinaUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> disciplinaUniqueViolationException(DisciplinaUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(DisciplinaEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> disciplinaEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(ProfInvalidException.class)
    public ResponseEntity<ErrorMessage> profInvalidException(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(ModalidadeUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> modalidadeUniqueViolationException(ModalidadeUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(ModalidadeEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> modalidadeEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(TurmaUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> turmaUniqueViolationException(TurmaUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(TurmaEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> turmaEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(CursoEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> cursoEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(CursoUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> cursoUniqueViolationException(CursoUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(TurnoEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> turnoEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(TurnoUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> turnoUniqueViolationException(TurnoUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(AulaEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> aulaEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(AulaUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> aulaUniqueViolationException(AulaUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(HorarioEntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> horarioEntityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(HorarioUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> horarioUniqueViolationException(HorarioUniqueViolationException ex, HttpServletRequest request){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, BindingResult result){

        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalidos", result));
    }
}
