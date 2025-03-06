package com.kanban.core.domain.dto.client;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientResponseTest {

    @Test
    public void testClientResponseToString() {
        DropdownResponse project = DropdownResponse.builder()
                .description("Project 1")
                .build();
        ClientResponse clientResponse = ClientResponse.builder()
                .id(1L)
                .name("Client 1")
                .project(project)
                .build();

        String expectedString = " ClientResponse = { id: 1, name: Client 1, project: Project 1}";
        assertEquals(expectedString, clientResponse.toString());
    }
}