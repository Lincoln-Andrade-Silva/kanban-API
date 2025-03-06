package com.kanban.util.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void testHandleApplicationBusinessException() {
        ApplicationBusinessException exception = mock(ApplicationBusinessException.class);
        when(exception.getHttpStatusCode()).thenReturn(400);

        HttpServletResponse response = mock(HttpServletResponse.class);

        ResponseEntity<Object> responseEntity = apiExceptionHandler.handleApplicationBusinessException(exception, response);

        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
        verify(response).setStatus(400);

        ApplicationErrorResponse errorResponse = (ApplicationErrorResponse) responseEntity.getBody();
        assert errorResponse != null;
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("Unexpected error");

        HttpServletResponse response = mock(HttpServletResponse.class);

        ResponseEntity<Object> responseEntity = apiExceptionHandler.handleException(exception, response);

        assert responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST;
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        ApplicationErrorResponse errorResponse = (ApplicationErrorResponse) responseEntity.getBody();
        assert errorResponse != null;
        assert errorResponse.getTitle().equals("Exception");
        assert errorResponse.getMessage().equals("Unexpected error");
    }
}