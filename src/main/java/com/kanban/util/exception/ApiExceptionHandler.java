package com.kanban.util.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @ExceptionHandler({ApplicationBusinessException.class})
    public ResponseEntity<Object> handleApplicationBusinessException(ApplicationBusinessException exception,
                                                                     HttpServletResponse servletResponse) {
        servletResponse.setStatus(exception.getHttpStatusCode());

        ApplicationErrorResponse errorResponse = ApplicationErrorResponse.setErrorResponse(exception);

        showLogError(errorResponse);
        return new ResponseEntity<>(errorResponse, headers(), HttpStatus.valueOf(exception.getHttpStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception, HttpServletResponse servletResponse) {
        servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        ApplicationErrorResponse errorResponse = ApplicationErrorResponse.setErrorResponse(exception);

        showLogError(errorResponse);
        return new ResponseEntity<>(errorResponse, headers(), HttpStatus.valueOf(HttpServletResponse.SC_BAD_REQUEST));
    }

    private static void showLogError(ApplicationErrorResponse errorResponse) {
        logger.error("Error Details, Title: {}, Message: {}", errorResponse.getTitle(), errorResponse.getMessage());
        logger.error("Error Occurred In, Path: {},  Method: {}, Line: {}",
                errorResponse.getPath(), errorResponse.getMethod(), errorResponse.getLine());
    }
}