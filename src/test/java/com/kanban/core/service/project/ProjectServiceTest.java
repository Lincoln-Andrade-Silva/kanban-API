package com.kanban.core.service.project;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.domain.dto.project.ProjectStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.repository.ClientRepository;
import com.kanban.core.domain.repository.ProjectRepository;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private Client client;
    private ProjectRequest projectRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setName("Client A");

        project = new Project();
        project.setId(1L);
        project.setStatus(ProjectStatus.CANCELADO);
        project.setName("Project A");

        projectRequest = new ProjectRequest();
        projectRequest.setName("Project A");
        projectRequest.setStatus("cancelado");
        projectRequest.setClientIds(Collections.singletonList(1L));
    }

    @Test
    public void testList() throws ApplicationBusinessException {
        Page<Project> projectPage = new PageImpl<>(Collections.singletonList(project));

        when(repository.findAll(any(Pageable.class))).thenReturn(projectPage);

        DataListResponse<ProjectResponse> response = projectService.list(1, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        assertEquals(1L, response.getTotalElements());
        assertEquals("Project A", response.getData().get(0).getName());
    }

    @Test
    public void testListClientsByProjectId() throws ApplicationBusinessException {
        Long projectId = 1L;
        List<Client> clients = Collections.singletonList(client);

        when(repository.findById(projectId)).thenReturn(Optional.of(project));
        when(clientRepository.findByProjectId(projectId)).thenReturn(clients);

        DataListResponse<DropdownResponse> response = projectService.listClientsByProjectId(projectId);

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals("Client A", response.getData().get(0).getDescription());
    }

    @Test
    public void testListClientsByProjectIdWithNoClients() throws ApplicationBusinessException {
        Long projectId = 1L;

        when(repository.findById(projectId)).thenReturn(Optional.of(project));
        when(clientRepository.findByProjectId(projectId)).thenReturn(new ArrayList<>());

        DataListResponse<DropdownResponse> response = projectService.listClientsByProjectId(projectId);

        assertNotNull(response);
    }

    @Test
    public void testListForDropdown() {
        when(repository.findAll(Sort.by(Sort.Order.desc("id")))).thenReturn(Collections.singletonList(project));

        DataListResponse<DropdownResponse> response = projectService.listForDropdown();

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals("Project A", response.getData().get(0).getDescription());
    }

    @Test
    public void testCreate() {
        List<Client> clientsFromDB = Collections.singletonList(client);
        Project project = new Project();
        project.setId(1L);
        project.setName("Project A");
        project.setStatus(ProjectStatus.CANCELADO);
        project.setClients(clientsFromDB);

        when(clientRepository.findAllById(projectRequest.getClientIds())).thenReturn(clientsFromDB);
        when(repository.save(any(Project.class))).thenReturn(project);

        DataResponse<ProjectResponse> response = projectService.create(projectRequest);

        assertNotNull(response);
        assertEquals("Project A", response.getData().getName());
    }

    @Test
    public void testEdit() throws ApplicationBusinessException {
        Long projectId = 1L;
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("Updated Project A");
        projectRequest.setStatus("em andamento");
        projectRequest.setClientIds(Collections.singletonList(1L));

        List<Client> clientsFromDB = Collections.singletonList(client);
        Project existingProject = new Project();
        existingProject.setId(projectId);
        existingProject.setName("Project A");

        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setStatus(ProjectStatus.EM_ANDAMENTO);
        updatedProject.setName("Updated Project A");

        when(repository.findById(projectId)).thenReturn(Optional.of(existingProject));
        when(clientRepository.findAllById(projectRequest.getClientIds())).thenReturn(clientsFromDB);
        when(repository.save(any(Project.class))).thenReturn(updatedProject);

        DataResponse<ProjectResponse> response = projectService.edit(projectId, projectRequest);

        assertNotNull(response);
        assertEquals("Updated Project A", response.getData().getName());
    }

    @Test
    public void testEdit_ProjectNotFound() {
        Long projectId = 1L;
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setName("Updated Project A");
        projectRequest.setStatus("em andamento");
        projectRequest.setClientIds(Collections.singletonList(1L));

        when(repository.findById(projectId)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            projectService.edit(projectId, projectRequest);
        });

        assertEquals("O projeto com ID 1 não foi encontrado", exception.getMessage());
    }


    @Test
    public void testDeleteById() throws ApplicationBusinessException {
        Long projectId = 1L;
        Project project = new Project();
        project.setClients(List.of(Client.builder().build()));
        project.setId(projectId);

        when(repository.findById(projectId)).thenReturn(Optional.of(project));
        doNothing().when(repository).deleteById(anyLong());

        projectService.deleteById(projectId);

        verify(repository, times(1)).deleteById(projectId);
    }

    @Test
    public void testDeleteById_ProjectNotFound() {
        Long projectId = 1L;
        when(repository.findById(projectId)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            projectService.deleteById(projectId);
        });

        assertEquals("O projeto com ID 1 não foi encontrado", exception.getMessage());
    }

    @Test
    public void testListClientsByProjectId_ProjectNotFound() {
        Long projectId = 1L;

        when(repository.findById(projectId)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            projectService.listClientsByProjectId(projectId);
        });

        assertEquals("O projeto com ID 1 não foi encontrado", exception.getMessage());
    }
}