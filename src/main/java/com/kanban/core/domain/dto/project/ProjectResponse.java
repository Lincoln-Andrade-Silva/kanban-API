package com.kanban.core.domain.dto.project;

import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.model.client.Client;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String status;
    private List<DropdownResponse> clients;

    @Override
    public String toString() {
        return "ProjectResponse = {" +
                " id: " + id +
                ", name: " + name +
                ", status: " + status +
                ", clients: " + (clients != null ? String.join(", ", clients.stream().map(DropdownResponse::getDescription).toList()) : "No data") +
                " }";
    }
}
