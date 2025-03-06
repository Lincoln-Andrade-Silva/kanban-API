package com.kanban.util.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ApplicationErrorResponse {
    private static final String COM_API = "com.kanban.";

    private int status;
    private String title;
    private String message;
    private String className;
    private String method;
    private String line;
    private String path;
    private String input;
    private LocalDateTime dateTime;

    public static ApplicationErrorResponse setErrorResponse(Exception exception) {
        ApplicationErrorResponse errorResponse = new ApplicationErrorResponse();
        String[] exceptionName = exception.getClass().getName().split("\\.");

        StackTraceElement stackTraceElement = null;
        if (Objects.nonNull(exception.getStackTrace()))
            stackTraceElement = filterStackTrace(exception.getStackTrace());

        if (exception instanceof ApplicationBusinessException) {
            errorResponse.setTitle(((ApplicationBusinessException) exception).getTitle());
            errorResponse.setStatus(((ApplicationBusinessException) exception).getHttpStatusCode());
            errorResponse.setInput(((ApplicationBusinessException) exception).getInput());
        } else {
            errorResponse.setTitle(exceptionName[exceptionName.length - 1]);
            errorResponse.setStatus(errorResponse.getStatus());
        }

        if (Objects.nonNull(stackTraceElement)) {
            errorResponse.setClassName(stackTraceElement.getFileName());
            errorResponse.setLine(String.valueOf(stackTraceElement.getLineNumber()));
            errorResponse.setMethod(stackTraceElement.getMethodName());
            errorResponse.setPath(stackTraceElement.getClassName());
        }
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setDateTime(LocalDateTime.now());

        return errorResponse;
    }

    private static StackTraceElement filterStackTrace(StackTraceElement[] stackTrace) {
        Optional<StackTraceElement> opt = Arrays.stream(stackTrace).filter(x ->
                x.getClassName().contains(COM_API)).findFirst();
        return opt.orElseGet(() -> stackTrace[0]);
    }
}