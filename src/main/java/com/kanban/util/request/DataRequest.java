package com.kanban.util.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataRequest<T> extends Request {
    @Serial
    private static final long serialVersionUID = -9199226445117528353L;

    private T data;
}