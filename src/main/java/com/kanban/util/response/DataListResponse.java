package com.kanban.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataListResponse<T> extends Response implements Serializable {

    @Serial
    private static final long serialVersionUID = -9024022837229919768L;

    private List<T> data;
    private int totalPages = 0;
    private Long totalElements = 0L;

}