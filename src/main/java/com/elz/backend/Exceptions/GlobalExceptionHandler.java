package com.elz.backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    //Gestion des erreures de validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidaionExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors=ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError->fieldError.getDefaultMessage() !=null
                            ?fieldError.getDefaultMessage()
                            : "Erreur de validation"
                ));
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), BAD_REQUEST.value(), "Erreur de validation",errors.toString());

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({ClientNotFoundException.class,})
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException ex){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",ex.getMessage());
        return new  ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler({ClientAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleRessourceAlreadyExisteException(ClientAlreadyExistsException exception){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(), CONFLICT.value(),
                "Ressouce Already Existe",exception.getMessage());
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception){
        ErrorResponse errorResponse=new ErrorResponse(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occured",exception.getMessage());
        return new ResponseEntity<>(errorResponse,INTERNAL_SERVER_ERROR);
    }



}
