package com.kanban.core.domain.model.client;

import com.kanban.core.domain.model.project.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testClientConstructor() {
        Project project = Project.builder().id(1L).name("Project A").build();
        Client client = Client.builder().id(1L).name("Client A").project(project).build();

        assertEquals(1L, client.getId());
        assertEquals("Client A", client.getName());
        assertEquals(project, client.getProject());
    }

    @Test
    public void testClientToString() {
        Project project = Project.builder().id(1L).name("Project A").build();
        Client client = Client.builder().id(1L).name("Client A").project(project).build();

        String expectedToString = "ClientResponse = {id: 1, name: Client A, project: Project A}";
        assertEquals(expectedToString, client.toString());
    }

    @Test
    public void testClientToStringWithoutProject() {
        Client client = Client.builder().id(1L).name("Client A").project(null).build();

        String expectedToString = "ClientResponse = {id: 1, name: Client A, project: No project}";
        assertEquals(expectedToString, client.toString());
    }

    @Test
    public void testClientSetters() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Client A");

        Project project = Project.builder().id(1L).name("Project A").build();
        client.setProject(project);

        assertEquals(1L, client.getId());
        assertEquals("Client A", client.getName());
        assertEquals(project, client.getProject());
    }
}
