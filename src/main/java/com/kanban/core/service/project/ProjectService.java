package com.kanban.core.service.project;

import com.kanban.core.domain.common.ProjectMessages;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.repository.ClientRepository;
import com.kanban.core.domain.repository.ProjectRepository;
import com.kanban.core.domain.validator.Validator;
import com.kanban.core.mapper.ProjectMapper;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final ProjectRepository repository;
    private final ClientRepository clientRepository;

    @Override
    public DataListResponse<ProjectResponse> list(Integer pageNumber, Integer pageSize) throws ApplicationBusinessException {
        int size = pageSize == null ? 10 : pageSize;
        int page = (pageNumber == null || pageNumber < 1) ? 0 : pageNumber - 1;

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Project> pageResult = repository.findAll(pageable);

        Validator.validatePageNotEmpty(pageResult, ProjectMessages.NO_PROJECTS_FOUND);

        List<ProjectResponse> responseList = ProjectMapper.entityToDTO(pageResult.getContent());

        return new DataListResponse<>(responseList, pageResult.getTotalPages(), pageResult.getTotalElements());
    }

    @Override
    public DataListResponse<DropdownResponse> listClientsByProjectId(Long projectId) throws ApplicationBusinessException {
        repository.findById(projectId)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        ProjectMessages.PROJECT_NOT_FOUND,
                        String.format(ProjectMessages.PROJECT_NOT_FOUND_BY_ID, projectId),
                        ""
                ));

        List<Client> clients = clientRepository.findByProjectId(projectId);

        if (clients.isEmpty()) {
            return new DataListResponse<>();
        }

        List<DropdownResponse> dropdownResponses = clients.stream()
                .map(client -> new DropdownResponse(client.getId(), client.getName()))
                .toList();

        return new DataListResponse<>(dropdownResponses, 1, (long) dropdownResponses.size());
    }

    @Override
    public DataListResponse<DropdownResponse> listForDropdown() {
        List<Project> projects = repository.findAll(Sort.by(Sort.Order.desc("id")));

        List<DropdownResponse> dropdownResponses = projects.stream()
                .map(project -> DropdownResponse.builder()
                        .id(project.getId())
                        .description(project.getName())
                        .build())
                .toList();

        return new DataListResponse<>(dropdownResponses, 0, (long) dropdownResponses.size());
    }

    @Override
    public DataResponse<ProjectResponse> create(ProjectRequest request) {
        List<Client> clientsFromDB = clientRepository.findAllById(request.getClientIds());

        Project project = ProjectMapper.requestToEntity(request, clientsFromDB);
        Project savedProject = repository.save(project);

        return new DataResponse<>(ProjectMapper.entityToDTO(savedProject), ProjectMessages.SUCCESSFUL_OPERATION);
    }

    @Override
    public DataResponse<ProjectResponse> edit(Long projectId, ProjectRequest request) throws ApplicationBusinessException {
        Project projectFromDB = repository.findById(projectId)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        ProjectMessages.PROJECT_NOT_FOUND,
                        String.format(ProjectMessages.PROJECT_NOT_FOUND_BY_ID, projectId),
                        ""
                ));

        List<Client> clientsFromDB = clientRepository.findAllById(request.getClientIds());

        ProjectMapper.updateEntityFromRequest(projectFromDB, request, clientsFromDB);
        Project updatedProject = repository.save(projectFromDB);

        return new DataResponse<>(ProjectMapper.entityToDTO(updatedProject), ProjectMessages.SUCCESSFUL_OPERATION);
    }

    @Override
    public void deleteById(Long id) throws ApplicationBusinessException {
        Project project = repository.findById(id)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        ProjectMessages.PROJECT_NOT_FOUND,
                        String.format(ProjectMessages.PROJECT_NOT_FOUND_BY_ID, id),
                        ""
                ));

        project.getClients().forEach(client -> client.setProject(null));

        repository.deleteById(project.getId());
    }
}