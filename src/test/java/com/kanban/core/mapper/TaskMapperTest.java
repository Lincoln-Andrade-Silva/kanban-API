package com.kanban.core.mapper;

import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.domain.dto.task.TaskStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.model.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskMapperTest {

    @Test
    public void testToEntity() {
        TaskRequest request = new TaskRequest();
        request.setName("Task Name");
        request.setStatus("Novo");

        Project project = mock(Project.class);
        when(project.getId()).thenReturn(1L);

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(2L);

        Task task = TaskMapper.toEntity(request, project, client);

        assertNotNull(task);
        assertEquals("Task Name", task.getName());
        assertEquals(TaskStatus.NOVO, task.getStatus());
        assertEquals(project, task.getProject());
        assertEquals(client, task.getClient());
    }

    @Test
    public void testToResponse() {
        Task task = mock(Task.class);
        when(task.getId()).thenReturn(1L);
        when(task.getName()).thenReturn("Task Name");
        when(task.getStatus()).thenReturn(TaskStatus.EM_PROGRESSO);

        Project project = mock(Project.class);
        when(task.getProject()).thenReturn(project);
        when(project.getId()).thenReturn(2L);

        Client client = mock(Client.class);
        when(task.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(3L);
        when(client.getName()).thenReturn("Client Name");

        TaskResponse response = TaskMapper.toResponse(task);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Task Name", response.getName());
        assertEquals(TaskStatus.EM_PROGRESSO.toString(), response.getStatus());
        assertEquals(2L, response.getProjectId());
        assertNotNull(response.getClient());
        assertEquals(3L, response.getClient().getId());
        assertEquals("Client Name", response.getClient().getDescription());
    }

    @Test
    public void testToResponse_nullClient() {
        Task task = mock(Task.class);
        when(task.getId()).thenReturn(1L);
        when(task.getName()).thenReturn("Task Name");
        when(task.getStatus()).thenReturn(TaskStatus.EM_TESTE);

        Project project = mock(Project.class);
        when(task.getProject()).thenReturn(project);
        when(project.getId()).thenReturn(2L);

        when(task.getClient()).thenReturn(null);

        TaskResponse response = TaskMapper.toResponse(task);

        assertNotNull(response);
        assertNull(response.getClient());
    }

    @Test
    public void testUpdateEntity() {
        TaskRequest request = new TaskRequest();
        request.setName("Updated Task");
        request.setStatus("Finalizado");

        Project project = mock(Project.class);
        when(project.getId()).thenReturn(1L);

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(2L);

        Task task = Task.builder().name("Old Task").status(TaskStatus.NOVO).project(project).client(client).build();

        Task updatedTask = TaskMapper.updateEntity(task, request, project, client);

        assertEquals("Updated Task", updatedTask.getName());
        assertEquals(TaskStatus.FINALIZADO, updatedTask.getStatus());
        assertEquals(project, updatedTask.getProject());
        assertEquals(client, updatedTask.getClient());
    }
}