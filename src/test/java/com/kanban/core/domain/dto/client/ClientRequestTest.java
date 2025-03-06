package com.kanban.core.domain.dto.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRequestTest {

    @Test
    public void testClientRequestToString() {
        ClientRequest clientRequest = ClientRequest.builder()
                .name("Client 1")
                .projectId(100L)
                .build();

        String expectedString = " ClientRequest = {, name: Client 1, projectId: 100}";
        assertEquals(expectedString, clientRequest.toString());
    }
}
