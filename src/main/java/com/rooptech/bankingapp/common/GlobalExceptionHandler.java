package com.rooptech.bankingapp.common;
import com.rooptech.bankingapp.account.exception.CustomerAlreadyExistException;
import com.rooptech.bankingapp.account.exception.ResourceNotFoundException;
import com.rooptech.bankingapp.card.exception.CardAlreadyExistsException;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatusCode status, WebRequest request) {
        List<ObjectError> objectErrorList = ex.getBindingResult().getAllErrors();
        Map<String, String> validationErrors = new HashMap<>();
        objectErrorList.forEach(
                error -> {
                    if (error instanceof FieldError fieldError){
                        String fieldName= fieldError.getField();
                        String errorMessage= fieldError.getDefaultMessage();
                        validationErrors.put(fieldName,errorMessage);
                    }
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(validationErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception exception
    ,WebRequest webRequest){
        ErrorResponseDto errorResponseDto =ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorTime(LocalDateTime.now())
                .errorMessage(exception.getMessage())
                .build();
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body(errorResponseDto);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(
            CustomerAlreadyExistException exception,
            WebRequest webRequest
    ){
      ErrorResponseDto errorResponseDto =
              new ErrorResponseDto(
                      webRequest.getDescription(false),
                      LocalDateTime.now(),
                      HttpStatus.CONFLICT.toString(),
                      exception.getMessage()
      ) ;
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(errorResponseDto);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ){
       ErrorResponseDto errorResponseDto =ErrorResponseDto.builder()
               .apiPath(webRequest.getDescription(false))
               .errorTime(LocalDateTime.now())
               .errorMessage(exception.getMessage())
               .errorCode(HttpStatus.NOT_FOUND.toString())
               .build();
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(errorResponseDto);
    }
    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(
            CardAlreadyExistsException exception,
            WebRequest webRequest
         ){

        ErrorResponseDto errorResponseDto =
        new ErrorResponseDto(
                webRequest.getDescription(false),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.toString(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponseDto);
    }
}
