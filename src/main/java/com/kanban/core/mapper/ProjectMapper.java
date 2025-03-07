package com.kanban.core.mapper;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.domain.dto.project.ProjectStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    private ProjectMapper() {
    }

    public static List<ProjectResponse> entityToDTO(List<Project> projects) {
        return projects.stream()
                .map(ProjectMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public static ProjectResponse entityToDTO(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .status(project.getStatus().getDescription())
                .clients(project.getClients() != null ?
                        project.getClients().stream()
                                .map(client -> DropdownResponse.builder().id(client.getId()).description(client.getName())
                                        .build()).toList()
                        : null)
                .build();
    }

    public static Project requestToEntity(ProjectRequest request, List<Client> clientsFromDB) {
        Project project = Project.builder()
                .name(request.getName())
                .status(ProjectStatus.fromDescription(request.getStatus()))
                .clients(null)
                .build();


        for (Client client : clientsFromDB) {
            client.setProject(project);
        }
        project.setClients(clientsFromDB);

        return project;
    }

    public static void updateEntityFromRequest(Project projectFromDB, ProjectRequest request, List<Client> clientsFromDB) {
        projectFromDB.setName(request.getName());
        projectFromDB.setStatus(ProjectStatus.fromDescription(request.getStatus()));

        List<Client> updatedClients = clientsFromDB.stream()
                .filter(client -> request.getClientIds().contains(client.getId()))
                .toList();

        projectFromDB.getClients().forEach(client -> {
            if (!updatedClients.contains(client)) {
                client.setProject(null);
            }
        });

        projectFromDB.getClients().clear();
        projectFromDB.getClients().addAll(updatedClients);

        updatedClients.forEach(client -> client.setProject(projectFromDB));
    }
}
