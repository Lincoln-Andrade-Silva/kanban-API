package com.kanban.core.service.client;

import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private Project project;
    private ClientRequest clientRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);
        project.setName("Project A");

        client = new Client();
        client.setId(1L);
        client.setName("Client A");
        client.setProject(project);

        clientRequest = new ClientRequest();
        clientRequest.setName("Client A");
        clientRequest.setProjectId(1L);
    }

    @Test
    public void testList() throws ApplicationBusinessException {
        Page<Client> page = mock(Page.class);

        when(clientRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(page.getContent()).thenReturn(Collections.singletonList(client));
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1L);

        DataListResponse<ClientResponse> response = clientService.list(1, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        assertEquals(1L, response.getTotalElements());
        assertEquals("Client A", response.getData().get(0).getName());
    }

    @Test
    public void testListForDropdown() {
        when(clientRepository.findAll(Sort.by(Sort.Order.desc("id"))))
                .thenReturn(Collections.singletonList(client));

        DataListResponse<DropdownResponse> response = clientService.listForDropdown();

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals("Client A", response.getData().get(0).getDescription());
    }

    @Test
    public void testCreate() throws ApplicationBusinessException {
        when(clientRepository.findByName(clientRequest.getName())).thenReturn(Optional.empty());
        when(projectRepository.findById(clientRequest.getProjectId())).thenReturn(Optional.of(project));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        DataResponse<ClientResponse> response = clientService.create(clientRequest);

        assertNotNull(response);
        assertEquals("Client A", response.getData().getName());
    }

    @Test
    public void testCreate_ClientAlreadyExists() throws ApplicationBusinessException {
        when(clientRepository.findByName(clientRequest.getName())).thenReturn(Optional.of(client));

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            clientService.create(clientRequest);
        });

        assertEquals("Cliente com o mesmo Name já existe", exception.getMessage());
    }

    @Test
    public void testEdit() throws ApplicationBusinessException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.findByNameAndDifferentId(clientRequest.getName(), 1L)).thenReturn(Optional.empty());
        when(projectRepository.findById(clientRequest.getProjectId())).thenReturn(Optional.of(project));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        DataResponse<ClientResponse> response = clientService.edit(1L, clientRequest);

        assertNotNull(response);
        assertEquals("Client A", response.getData().getName());
    }

    @Test
    public void testEdit_ClientNotFound() throws ApplicationBusinessException {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            clientService.edit(1L, clientRequest);
        });

        assertEquals("Cliente não encontrado para o ID: 1", exception.getMessage());
    }

    @Test
    public void testDeleteById() throws ApplicationBusinessException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.deleteById(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteById_ClientNotFound() throws ApplicationBusinessException {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            clientService.deleteById(1L);
        });

        assertEquals("Cliente não encontrado para o ID: 1", exception.getMessage());
    }

    @Test
    public void testCreate_NoProjectId() throws ApplicationBusinessException {
        clientRequest.setProjectId(0L);

        when(clientRepository.findByName(clientRequest.getName())).thenReturn(Optional.empty());
        when(projectRepository.findById(clientRequest.getProjectId())).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        DataResponse<ClientResponse> response = clientService.create(clientRequest);

        assertNotNull(response);
        assertEquals("Client A", response.getData().getName());
    }

}