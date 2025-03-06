package com.kanban.util.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Setter
@Getter
public class ApplicationBusinessException extends Exception {
    @Serial
    private static final long serialVersionUID = 2594220962674232227L;
    private String title;
    private String input;
    private String message;
    private int httpStatusCode;

    public ApplicationBusinessException(String title, String message) {
        this.setTitle(title);
        this.setMessage(message);
        this.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public ApplicationBusinessException(String title, String message, String input) {
        this.setTitle(title);
        this.setInput(input);
        this.setMessage(message);
        this.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
    }

    public ApplicationBusinessException(int httpStatusCode, String title, String message, String input) {
        this.title = title;
        this.message = message;
        this.setInput(input);
        this.httpStatusCode = httpStatusCode;
    }

    public ApplicationBusinessException(int httpStatusCode, String title, String message) {
        this.title = title;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}