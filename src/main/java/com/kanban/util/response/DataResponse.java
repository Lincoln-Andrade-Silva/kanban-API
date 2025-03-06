package com.kanban.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<T> extends Response {

    @Serial
    private static final long serialVersionUID = 5605829304634L;

    private T data;

    public DataResponse(T data, String message) {
        this.setData(data);
        this.setMessage(message);
    }
}