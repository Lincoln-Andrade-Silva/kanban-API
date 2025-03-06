package com.kanban.core.domain.dto.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRequestTest {

    @Test
    public void testConstructorAndGetters() {
        TaskRequest taskRequest = new TaskRequest("Task Name", "Em Andamento", 1L, 2L);

        assertEquals("Task Name", taskRequest.getName());
        assertEquals("Em Andamento", taskRequest.getStatus());
        assertEquals(1L, taskRequest.getProjectId());
        assertEquals(2L, taskRequest.getClientId());
    }

    @Test
    public void testBuilder() {
        TaskRequest taskRequest = TaskRequest.builder()
                .name("Task Name")
                .status("Finalizado")
                .projectId(3L)
                .clientId(4L)
                .build();

        assertEquals("Task Name", taskRequest.getName());
        assertEquals("Finalizado", taskRequest.getStatus());
        assertEquals(3L, taskRequest.getProjectId());
        assertEquals(4L, taskRequest.getClientId());
    }

    @Test
    public void testNoArgsConstructor() {
        TaskRequest taskRequest = new TaskRequest();

        assertNull(taskRequest.getName());
        assertNull(taskRequest.getStatus());
        assertNull(taskRequest.getProjectId());
        assertNull(taskRequest.getClientId());
    }

    @Test
    public void testToString() {
        TaskRequest taskRequest = TaskRequest.builder()
                .name("Task Name")
                .status("Em Andamento")
                .projectId(1L)
                .clientId(2L)
                .build();

        String expectedString = "TaskRequest(name=Task Name, status=Em Andamento, projectId=1, clientId=2)";
        assertEquals(expectedString, taskRequest.toString());
    }
}