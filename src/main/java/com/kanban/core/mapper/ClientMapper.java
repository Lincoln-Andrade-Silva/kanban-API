package com.kanban.core.mapper;

import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    private ClientMapper() {
    }

    public static List<ClientResponse> entityToDTO(List<Client> clients) {
        return clients.stream()
                .map(ClientMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public static ClientResponse entityToDTO(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .project(client.getProject() != null ? DropdownResponse.builder().id(client.getProject().getId())
                        .description(client.getProject().getName()).build() : null)
                .build();
    }

    public static Client requestToEntity(ClientRequest request, Project project) {
        return Client.builder().name(request.getName()).project(project).build();
    }

    public static void updateEntityFromRequest(Client client, ClientRequest request, Project project) {
        client.setName(request.getName());
        client.setProject(project);
    }
}