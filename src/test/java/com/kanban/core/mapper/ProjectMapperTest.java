package com.kanban.core.mapper;

import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.domain.dto.project.ProjectStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectMapperTest {

    @Test
    public void testEntityToDTO_singleProject() {
        ProjectStatus status = mock(ProjectStatus.class);
        when(status.getDescription()).thenReturn("Active");

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(1L);
        when(client.getName()).thenReturn("Client Name");

        Project project = Project.builder().id(1L).name("Project Name").status(status)
                .clients(List.of(client)).build();

        ProjectResponse response = ProjectMapper.entityToDTO(project);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Project Name", response.getName());
        assertEquals("Active", response.getStatus());
        assertNotNull(response.getClients());
        assertEquals(1, response.getClients().size());
        assertEquals(1L, response.getClients().get(0).getId());
        assertEquals("Client Name", response.getClients().get(0).getDescription());
    }

    @Test
    public void testEntityToDTO_nullClients() {
        ProjectStatus status = mock(ProjectStatus.class);
        when(status.getDescription()).thenReturn("Active");

        Project project = Project.builder().id(1L).name("Project Name").status(status)
                .clients(null).build();

        ProjectResponse response = ProjectMapper.entityToDTO(project);

        assertNotNull(response);
        assertNull(response.getClients());
    }

    @Test
    public void testEntityToDTO_multipleProjects() {
        ProjectStatus status = mock(ProjectStatus.class);
        when(status.getDescription()).thenReturn("Active");

        Client client1 = mock(Client.class);
        when(client1.getId()).thenReturn(1L);
        when(client1.getName()).thenReturn("Client Name 1");

        Client client2 = mock(Client.class);
        when(client2.getId()).thenReturn(2L);
        when(client2.getName()).thenReturn("Client Name 2");

        Project project1 = Project.builder().id(1L).name("Project Name 1").status(status)
                .clients(List.of(client1)).build();
        Project project2 = Project.builder().id(2L).name("Project Name 2").status(status)
                .clients(List.of(client2)).build();

        List<ProjectResponse> responses = ProjectMapper.entityToDTO(List.of(project1, project2));

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Project Name 1", responses.get(0).getName());
        assertEquals("Project Name 2", responses.get(1).getName());
    }

    @Test
    public void testRequestToEntity() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Project Name");
        request.setStatus("Finalizado");

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(1L);
        List<Client> clientsFromDB = List.of(client);

        Project project = ProjectMapper.requestToEntity(request, clientsFromDB);

        assertNotNull(project);
        assertEquals("Project Name", project.getName());
        assertEquals(ProjectStatus.FINALIZADO, project.getStatus());
        assertEquals(1, project.getClients().size());
        assertEquals(client, project.getClients().get(0));
    }

    @Test
    public void testUpdateEntityFromRequest_withClients() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Updated Project");
        request.setStatus("Cancelado");
        request.setClientIds(List.of(1L));

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(1L);
        List<Client> clientsFromDB = List.of(client);

        Project project = Project.builder().id(1L).name("Old Project").status(ProjectStatus.FINALIZADO)
                .clients(clientsFromDB).build();

        ProjectMapper.updateEntityFromRequest(project, request, clientsFromDB);

        assertEquals("Updated Project", project.getName());
        assertEquals(ProjectStatus.CANCELADO, project.getStatus());
        assertNotNull(project.getClients());
        assertEquals(1, project.getClients().size());
        assertEquals(client, project.getClients().get(0));
    }

    @Test
    public void testUpdateEntityFromRequest_noClients() {
        ProjectRequest request = new ProjectRequest();
        request.setName("Updated Project");
        request.setStatus("Cancelado");
        request.setClientIds(List.of());

        Client client = mock(Client.class);
        List<Client> clientsFromDB = List.of(client);

        Project project = Project.builder().id(1L).name("Old Project").status(ProjectStatus.FINALIZADO)
                .clients(clientsFromDB).build();

        ProjectMapper.updateEntityFromRequest(project, request, clientsFromDB);

        assertEquals("Updated Project", project.getName());
        assertEquals(ProjectStatus.CANCELADO, project.getStatus());
        assertNull(project.getClients());
    }
}