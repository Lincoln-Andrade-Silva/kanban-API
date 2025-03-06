package com.kanban.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 8927944169026414109L;

    private String input;
    private String message;
}