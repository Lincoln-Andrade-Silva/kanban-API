package com.kanban.util.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class FilterRequest<T> extends Request {

    @Serial
    private static final long serialVersionUID = 4754856071150007342L;

    private T data;
    private String locale;
    private String strSearch;
}