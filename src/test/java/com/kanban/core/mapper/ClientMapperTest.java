package com.kanban.core.mapper;

import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientMapperTest {

    @Test
    public void testEntityToDTO_singleClient() {
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(1L);
        when(project.getName()).thenReturn("Project Name");

        Client client = Client.builder().id(1L).name("Client Name").project(project).build();

        ClientResponse response = ClientMapper.entityToDTO(client);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Client Name", response.getName());
        assertNotNull(response.getProject());
        assertEquals(1L, response.getProject().getId());
        assertEquals("Project Name", response.getProject().getDescription());
    }

    @Test
    public void testEntityToDTO_nullProject() {
        Client client = Client.builder().id(1L).name("Client Name").build();

        ClientResponse response = ClientMapper.entityToDTO(client);

        assertNotNull(response);
        assertNull(response.getProject());
    }

    @Test
    public void testEntityToDTO_multipleClients() {
        Project project = mock(Project.class);
        when(project.getId()).thenReturn(1L);
        when(project.getName()).thenReturn("Project Name");

        Client client1 = Client.builder().id(1L).name("Client Name 1").project(project).build();
        Client client2 = Client.builder().id(2L).name("Client Name 2").project(project).build();

        List<ClientResponse> responses = ClientMapper.entityToDTO(List.of(client1, client2));

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Client Name 1", responses.get(0).getName());
        assertEquals("Client Name 2", responses.get(1).getName());
    }

    @Test
    public void testRequestToEntity() {
        Project project = mock(Project.class);
        ClientRequest request = new ClientRequest();
        request.setName("Client Name");

        Client client = ClientMapper.requestToEntity(request, project);

        assertNotNull(client);
        assertEquals("Client Name", client.getName());
        assertEquals(project, client.getProject());
    }

    @Test
    public void testUpdateEntityFromRequest() {
        Project project = mock(Project.class);
        Client client = Client.builder().name("Old Name").project(project).build();
        ClientRequest request = new ClientRequest();
        request.setName("New Name");

        ClientMapper.updateEntityFromRequest(client, request, project);

        assertEquals("New Name", client.getName());
        assertEquals(project, client.getProject());
    }
}