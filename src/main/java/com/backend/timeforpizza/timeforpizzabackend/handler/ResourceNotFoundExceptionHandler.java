package com.backend.timeforpizza.timeforpizzabackend.handler;

import com.backend.timeforpizza.timeforpizzabackend.dto.APIErrorDTO;
import com.backend.timeforpizza.timeforpizzabackend.dto.HttpErrorDTO;
import com.backend.timeforpizza.timeforpizzabackend.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorDTO> interceptResourceNotFoundException(ResourceNotFoundException ex, HttpServletResponse response) {
        String message = ex.getResourceName() + " not found with" + ex.getFieldName() + " : " + ex.getFieldValue();
        logger.error(message);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HttpErrorDTO error = new HttpErrorDTO(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value());
        APIErrorDTO apiError = new APIErrorDTO(timestamp, message, error);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
