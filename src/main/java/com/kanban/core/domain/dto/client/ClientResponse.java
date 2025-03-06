package com.kanban.core.domain.dto.client;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private DropdownResponse project;

    @Override
    public String toString() {
        return " ClientResponse = {" +
                " id: " + id +
                ", name: " + name +
                ", project: " + project.getDescription() +
                '}';
    }
}
