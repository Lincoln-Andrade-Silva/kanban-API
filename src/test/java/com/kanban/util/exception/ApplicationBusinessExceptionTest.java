package com.kanban.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ApplicationBusinessExceptionTest {

    @Test
    void testConstructorWithTitleAndMessage() {
        String title = "Business error";
        String message = "Specific business logic error";
        ApplicationBusinessException exception = new ApplicationBusinessException(title, message);

        assertEquals(title, exception.getTitle());
        assertEquals(message, exception.getMessage());
        assertEquals(400, exception.getHttpStatusCode());
    }

    @Test
    void testConstructorWithTitleMessageAndInput() {
        String title = "Business error";
        String message = "Specific business logic error";
        String input = "Invalid input";
        ApplicationBusinessException exception = new ApplicationBusinessException(title, message, input);

        assertEquals(title, exception.getTitle());
        assertEquals(message, exception.getMessage());
        assertEquals(input, exception.getInput());
        assertEquals(400, exception.getHttpStatusCode());
    }

    @Test
    void testConstructorWithAllParams() {
        int httpStatusCode = 404;
        String title = "Not Found";
        String message = "Resource not found";
        String input = "resourceId";
        ApplicationBusinessException exception = new ApplicationBusinessException(httpStatusCode, title, message, input);

        assertEquals(httpStatusCode, exception.getHttpStatusCode());
        assertEquals(title, exception.getTitle());
        assertEquals(message, exception.getMessage());
        assertEquals(input, exception.getInput());
    }

    @Test
    void testConstructorWithTitleAndMessageWithoutInput() {
        int httpStatusCode = 500;
        String title = "Internal Server Error";
        String message = "Unexpected error";
        ApplicationBusinessException exception = new ApplicationBusinessException(httpStatusCode, title, message);

        assertEquals(httpStatusCode, exception.getHttpStatusCode());
        assertEquals(title, exception.getTitle());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getInput());
    }
}