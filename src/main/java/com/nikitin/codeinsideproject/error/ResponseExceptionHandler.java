package com.nikitin.codeinsideproject.error;

import com.nikitin.codeinsideproject.util.Response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        GenericResponse bodyOfResponse =
                new GenericResponse(result.getFieldErrors(), result.getGlobalErrors());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ PersonAlreadyExistException.class })
    public ResponseEntity<?> handleUserAlreadyExist(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyResponse = new GenericResponse("Person Already Exist with email or username", "UserAlreadyExist");
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({ PersonNotFoundException.class })
    public ResponseEntity<?> handlePersonNotFound(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyResponse = new GenericResponse("Profile not found", "PersonNotFound");
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ NotesNotFoundException.class })
    public ResponseEntity<?> handleNotesNotFound(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyResponse = new GenericResponse("Notes not found", "NotesNotFound");
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
