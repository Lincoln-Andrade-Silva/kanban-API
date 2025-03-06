package com.kanban.core.domain.model.task;

import com.kanban.core.domain.dto.task.TaskStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskConstructor() {
        Project project = Project.builder().id(1L).name("Project A").build();
        Client client = Client.builder().id(1L).name("Client A").build();

        Task task = Task.builder()
                .id(1L)
                .name("Task A")
                .status(TaskStatus.NOVO)
                .project(project)
                .client(client)
                .build();

        assertEquals(1L, task.getId());
        assertEquals("Task A", task.getName());
        assertEquals(TaskStatus.NOVO, task.getStatus());
        assertEquals(project, task.getProject());
        assertEquals(client, task.getClient());
    }

    @Test
    public void testTaskToString() {
        Project project = Project.builder().id(1L).name("Project A").build();
        Client client = Client.builder().id(1L).name("Client A").build();

        Task task = Task.builder()
                .id(1L)
                .name("Task A")
                .status(TaskStatus.NOVO)
                .project(project)
                .client(client)
                .build();

        String expectedToString = "Task = {id: 1, name: Task A, status: Novo, project: Project A, client: Client A}";
        assertEquals(expectedToString, task.toString());
    }

    @Test
    public void testTaskToStringWithoutClient() {
        Project project = Project.builder().id(1L).name("Project A").build();

        Task task = Task.builder()
                .id(1L)
                .name("Task A")
                .status(TaskStatus.NOVO)
                .project(project)
                .client(null)
                .build();

        String expectedToString = "Task = {id: 1, name: Task A, status: Novo, project: Project A, client: No client}";
        assertEquals(expectedToString, task.toString());
    }

    @Test
    public void testTaskSetters() {
        Project project = Project.builder().id(1L).name("Project A").build();
        Client client = Client.builder().id(1L).name("Client A").build();

        Task task = new Task();
        task.setId(1L);
        task.setName("Task A");
        task.setStatus(TaskStatus.NOVO);
        task.setProject(project);
        task.setClient(client);

        assertEquals(1L, task.getId());
        assertEquals("Task A", task.getName());
        assertEquals(TaskStatus.NOVO, task.getStatus());
        assertEquals(project, task.getProject());
        assertEquals(client, task.getClient());
    }
}
