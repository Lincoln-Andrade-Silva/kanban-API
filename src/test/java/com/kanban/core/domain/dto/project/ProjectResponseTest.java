package com.kanban.core.domain.dto.project;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectResponseTest {

    @Test
    public void testProjectResponseBuilder() {
        DropdownResponse client1 = DropdownResponse.builder().id(1L).description("Client A").build();
        DropdownResponse client2 = DropdownResponse.builder().id(2L).description("Client B").build();

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name("Project A")
                .status("Active")
                .clients(Arrays.asList(client1, client2))
                .build();

        assertNotNull(projectResponse);
        assertEquals(1L, projectResponse.getId());
        assertEquals("Project A", projectResponse.getName());
        assertEquals("Active", projectResponse.getStatus());
        assertEquals(2, projectResponse.getClients().size());
        assertEquals("Client A", projectResponse.getClients().get(0).getDescription());
        assertEquals("Client B", projectResponse.getClients().get(1).getDescription());
    }

    @Test
    public void testProjectResponseToString() {
        DropdownResponse client1 = DropdownResponse.builder().id(1L).description("Client A").build();
        DropdownResponse client2 = DropdownResponse.builder().id(2L).description("Client B").build();

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name("Project A")
                .status("Active")
                .clients(Arrays.asList(client1, client2))
                .build();

        String expectedString = "ProjectResponse = { id: 1, name: Project A, status: Active, clients: Client A, Client B }";
        assertEquals(expectedString, projectResponse.toString());
    }

    @Test
    public void testProjectResponseToStringWithNoClients() {
        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name("Project A")
                .status("Active")
                .clients(null)
                .build();

        String expectedString = "ProjectResponse = { id: 1, name: Project A, status: Active, clients: No data }";
        assertEquals(expectedString, projectResponse.toString());
    }
}