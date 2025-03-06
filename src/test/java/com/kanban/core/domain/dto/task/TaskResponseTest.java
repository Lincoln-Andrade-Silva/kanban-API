package com.kanban.core.domain.dto.task;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskResponseTest {

    @Test
    public void testConstructorAndGetters() {
        DropdownResponse clientResponse = new DropdownResponse(1L, "Client Name");
        TaskResponse taskResponse = new TaskResponse(1L, "Task Name", "Em Andamento", 2L, clientResponse);

        assertEquals(1L, taskResponse.getId());
        assertEquals("Task Name", taskResponse.getName());
        assertEquals("Em Andamento", taskResponse.getStatus());
        assertEquals(2L, taskResponse.getProjectId());
        assertEquals(clientResponse, taskResponse.getClient());
    }

    @Test
    public void testBuilder() {
        DropdownResponse clientResponse = new DropdownResponse(1L, "Client Name");
        TaskResponse taskResponse = TaskResponse.builder()
                .id(1L)
                .name("Task Name")
                .status("Finalizado")
                .projectId(3L)
                .client(clientResponse)
                .build();

        assertEquals(1L, taskResponse.getId());
        assertEquals("Task Name", taskResponse.getName());
        assertEquals("Finalizado", taskResponse.getStatus());
        assertEquals(3L, taskResponse.getProjectId());
        assertEquals(clientResponse, taskResponse.getClient());
    }

    @Test
    public void testNoArgsConstructor() {
        TaskResponse taskResponse = new TaskResponse();

        assertNull(taskResponse.getId());
        assertNull(taskResponse.getName());
        assertNull(taskResponse.getStatus());
        assertNull(taskResponse.getProjectId());
        assertNull(taskResponse.getClient());
    }

    @Test
    public void testToString() {
        DropdownResponse clientResponse = new DropdownResponse(1L, "Client Name");
        TaskResponse taskResponse = TaskResponse.builder()
                .id(1L)
                .name("Task Name")
                .status("Em Andamento")
                .projectId(2L)
                .client(clientResponse)
                .build();

        String expectedString = " ProjectResponse = { id: 1, name: Task Name, status: Em Andamento, client: Client Name }";
        assertEquals(expectedString, taskResponse.toString());
    }

    @Test
    public void testToStringNoClient() {
        TaskResponse taskResponse = TaskResponse.builder()
                .id(1L)
                .name("Task Name")
                .status("Em Andamento")
                .projectId(2L)
                .client(null)
                .build();

        String expectedString = " ProjectResponse = { id: 1, name: Task Name, status: Em Andamento, client: No data }";
        assertEquals(expectedString, taskResponse.toString());
    }
}
