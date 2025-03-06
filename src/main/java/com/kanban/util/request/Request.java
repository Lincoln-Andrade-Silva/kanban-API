package com.kanban.util.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 5475411755679503065L;

    private String locale;
}