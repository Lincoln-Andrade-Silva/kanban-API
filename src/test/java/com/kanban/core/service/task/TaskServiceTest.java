package com.kanban.core.service.task;

import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.domain.dto.task.TaskStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.model.task.Task;
import com.kanban.core.domain.repository.ClientRepository;
import com.kanban.core.domain.repository.ProjectRepository;
import com.kanban.core.domain.repository.TaskRepository;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private Client client;
    private Project project;
    private TaskRequest taskRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setName("Client A");

        project = new Project();
        project.setId(1L);
        project.setName("Project A");

        task = new Task();
        task.setId(1L);
        task.setName("Task A");
        task.setStatus(TaskStatus.EM_TESTE);
        task.setProject(project);
        task.setClient(client);

        taskRequest = new TaskRequest();
        taskRequest.setProjectId(1L);
        taskRequest.setClientId(1L);
        taskRequest.setStatus("novo");
        taskRequest.setName("Task A");
    }

    @Test
    public void testList() {
        when(taskRepository.findByProjectId(1)).thenReturn(Collections.singletonList(task));

        DataListResponse<TaskResponse> response = taskService.list(1);

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals("Task A", response.getData().get(0).getName());
    }

    @Test
    public void testCreate() throws ApplicationBusinessException {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        DataResponse<TaskResponse> response = taskService.create(taskRequest);

        assertNotNull(response);
        assertEquals("Task A", response.getData().getName());
    }

    @Test
    public void testCreate_ProjectNotFound() throws ApplicationBusinessException {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            taskService.create(taskRequest);
        });

        assertEquals("O projeto com ID 1 não foi encontrado", exception.getMessage());
    }

    @Test
    public void testEdit() throws ApplicationBusinessException {
        TaskRequest updatedTaskRequest = new TaskRequest();
        updatedTaskRequest.setName("Updated Task A");
        updatedTaskRequest.setProjectId(1L);
        updatedTaskRequest.setClientId(1L);
        updatedTaskRequest.setStatus("novo");

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Task A");
        updatedTask.setStatus(TaskStatus.EM_TESTE);
        updatedTask.setProject(project);
        updatedTask.setClient(client);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        DataResponse<TaskResponse> response = taskService.edit(1L, updatedTaskRequest);

        assertNotNull(response);
        assertEquals("Updated Task A", response.getData().getName());
    }

    @Test
    public void testEdit_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            taskService.edit(1L, taskRequest);
        });

        assertEquals("A tarefa com ID 1 não foi encontrada", exception.getMessage());
    }

    @Test
    public void testEdit_ProjectNotFound() {
        TaskRequest updatedTaskRequest = new TaskRequest();
        updatedTaskRequest.setName("Updated Task A");
        updatedTaskRequest.setProjectId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            taskService.edit(1L, updatedTaskRequest);
        });

        assertEquals("O projeto com ID 1 não foi encontrado", exception.getMessage());
    }

    @Test
    public void testDelete() throws ApplicationBusinessException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDelete_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationBusinessException exception = assertThrows(ApplicationBusinessException.class, () -> {
            taskService.delete(1L);
        });

        assertEquals("A tarefa com ID 1 não foi encontrada", exception.getMessage());
    }
}