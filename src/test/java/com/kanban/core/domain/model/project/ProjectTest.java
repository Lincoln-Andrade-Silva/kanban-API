package com.kanban.core.domain.model.project;

import com.kanban.core.domain.dto.project.ProjectStatus;
import com.kanban.core.domain.model.client.Client;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void testProjectConstructor() {
        Client client1 = Client.builder().id(1L).name("Client A").build();
        Client client2 = Client.builder().id(2L).name("Client B").build();
        List<Client> clients = List.of(client1, client2);

        Project project = Project.builder()
                .id(1L)
                .name("Project A")
                .status(ProjectStatus.EM_ANDAMENTO)
                .clients(clients)
                .build();

        assertEquals(1L, project.getId());
        assertEquals("Project A", project.getName());
        assertEquals(ProjectStatus.EM_ANDAMENTO, project.getStatus());
        assertEquals(clients, project.getClients());
    }

    @Test
    public void testProjectToString() {
        Client client1 = Client.builder().id(1L).name("Client A").build();
        Client client2 = Client.builder().id(2L).name("Client B").build();
        List<Client> clients = List.of(client1, client2);

        Project project = Project.builder()
                .id(1L)
                .name("Project A")
                .status(ProjectStatus.EM_ANDAMENTO)
                .clients(clients)
                .build();

        String expectedToString = "ProjectResponse = {id: 1, name: Project A, status: Em Andamento, clients: Client A, Client B}";
        assertEquals(expectedToString, project.toString());
    }

    @Test
    public void testProjectToStringWithoutClients() {
        Project project = Project.builder()
                .id(1L)
                .name("Project A")
                .status(ProjectStatus.EM_ANDAMENTO)
                .clients(null)
                .build();

        String expectedToString = "ProjectResponse = {id: 1, name: Project A, status: Em Andamento, clients: No clients}";
        assertEquals(expectedToString, project.toString());
    }

    @Test
    public void testProjectSetters() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setStatus(ProjectStatus.EM_ANDAMENTO);

        Client client1 = Client.builder().id(1L).name("Client A").build();
        Client client2 = Client.builder().id(2L).name("Client B").build();
        project.setClients(List.of(client1, client2));

        assertEquals(1L, project.getId());
        assertEquals("Project A", project.getName());
        assertEquals(ProjectStatus.EM_ANDAMENTO, project.getStatus());
        assertEquals(List.of(client1, client2), project.getClients());
    }
}
