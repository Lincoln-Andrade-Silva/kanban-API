package com.kanban.core.domain.dto.task;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String status;
    private Long projectId;
    private DropdownResponse client;

    @Override
    public String toString() {
        return " ProjectResponse = {" +
                " id: " + id +
                ", name: " + name +
                ", status: " + status +
                ", client: " + (client != null ? client.getDescription() : "No data") +
                " }";
    }
}
