package com.kanban.core.service.task;

import com.kanban.core.domain.common.TaskMessages;
import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.model.task.Task;
import com.kanban.core.domain.repository.ClientRepository;
import com.kanban.core.domain.repository.ProjectRepository;
import com.kanban.core.domain.repository.TaskRepository;
import com.kanban.core.mapper.TaskMapper;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository repository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;

    @Override
    public DataListResponse<TaskResponse> list(Integer projectId) {
        List<Task> tasks = repository.findByProjectId(projectId);

        List<TaskResponse> taskResponses = tasks.stream()
                .map(TaskMapper::toResponse)
                .sorted((task1, task2) -> Long.compare(task2.getId(), task1.getId()))
                .toList();

        return new DataListResponse<>(taskResponses, 0, (long) taskResponses.size());
    }

    @Override
    public DataResponse<TaskResponse> create(TaskRequest request) throws ApplicationBusinessException {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        TaskMessages.TASK_PROJECT_NOT_FOUND,
                        String.format(TaskMessages.TASK_PROJECT_NOT_FOUND_BY_ID, request.getProjectId()),
                        ""
                ));

        Client client = request.getClientId() != null ?
                clientRepository.findById(request.getClientId()).orElse(null) : null;

        Task task = TaskMapper.toEntity(request, project, client);
        task = repository.save(task);

        return new DataResponse<>(TaskMapper.toResponse(task));
    }

    @Override
    public DataResponse<TaskResponse> edit(Long taskId, TaskRequest request) throws ApplicationBusinessException {
        Task task = repository.findById(taskId)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        TaskMessages.TASK_NOT_FOUND,
                        String.format(TaskMessages.TASK_NOT_FOUND_BY_ID, taskId),
                        ""
                ));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        TaskMessages.TASK_PROJECT_NOT_FOUND,
                        String.format(TaskMessages.TASK_PROJECT_NOT_FOUND_BY_ID, request.getProjectId()),
                        ""
                ));

        Client client = request.getClientId() != null ?
                clientRepository.findById(request.getClientId()).orElse(null) : null;

        Task updatedTask = TaskMapper.updateEntity(task, request, project, client);
        updatedTask = repository.save(updatedTask);

        return new DataResponse<>(TaskMapper.toResponse(updatedTask));
    }

    @Override
    public void delete(Long taskId) throws ApplicationBusinessException {
        Task task = repository.findById(taskId)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        TaskMessages.TASK_NOT_FOUND,
                        String.format(TaskMessages.TASK_NOT_FOUND_BY_ID, taskId),
                        ""
                ));

        repository.deleteById(task.getId());
    }
}