package com.algovision.exception;
import com.algovision.dto.AlgorithmResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AlgorithmResponse> handleIllegal(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(AlgorithmResponse.error(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AlgorithmResponse> handleGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(AlgorithmResponse.error("Internal error: " + e.getMessage()));
    }
}
