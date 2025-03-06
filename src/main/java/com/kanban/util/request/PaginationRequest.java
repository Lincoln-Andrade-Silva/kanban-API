package com.kanban.util.request;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PaginationRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6869286734342943565L;

    private Pageable page;
    private String sortByAttribute;
    private Boolean ascendingOrder;

    public PaginationRequest(Integer page, Integer pageSize, String sortByAttribute, Boolean ascendingOrder) {
        if (page != null && pageSize != null) {
            this.page = PageRequest.of(page, pageSize);
        } else {
            this.page = PageRequest.of(0, 2147483647);
        }

        this.sortByAttribute = sortByAttribute;
        this.ascendingOrder = ascendingOrder == null ? Boolean.TRUE : ascendingOrder;
    }
}