package com.kanban.core.mapper;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.domain.dto.task.TaskStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.model.task.Task;

public class TaskMapper {
    private TaskMapper() {
    }

    public static Task toEntity(TaskRequest request, Project project, Client client) {
        return Task.builder()
                .name(request.getName())
                .status(TaskStatus.fromDescription(request.getStatus().toUpperCase()))
                .project(project)
                .client(client)
                .build();
    }

    public static TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .status(task.getStatus().name())
                .projectId(task.getProject().getId())
                .client(task.getClient() != null ? DropdownResponse.builder().id(task.getClient().getId())
                        .description(task.getClient().getName()).build() : null)
                .build();
    }

    public static Task updateEntity(Task task, TaskRequest request, Project project, Client client) {
        task.setClient(client);
        task.setProject(project);
        task.setName(request.getName());
        task.setStatus(TaskStatus.fromDescription(request.getStatus()));
        return task;
    }

}
