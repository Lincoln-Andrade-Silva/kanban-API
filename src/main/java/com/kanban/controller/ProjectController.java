package com.kanban.controller;

import com.kanban.core.domain.common.DomainReturnCode;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.service.project.IProjectService;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "project")
@CrossOrigin(origins = "${api.access.control.allow.origin}")
@Tag(name = "Project Controller", description = "Endpoints of Project Controller")
public class ProjectController {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    private static final String STARTED = " - Started";
    private static final String FINISHED = " - Finished";

    private final IProjectService service;

    @GetMapping()
    @Operation(summary = "List Projects", description = "List All Projects from DB")
    public DataListResponse<ProjectResponse> list(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize
    ) throws ApplicationBusinessException {
        log.info("List method" + STARTED);
        log.debug("List method params, Page:{}, Page Size: {}", page, pageSize);

        DataListResponse<ProjectResponse> response = service.list(page, pageSize);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("List method" + FINISHED);
        return response;
    }

    @GetMapping("/{projectId}/clients")
    @Operation(summary = "List Clients for a Project", description = "List Clients associated with a specific project")
    public DataListResponse<DropdownResponse> listClientsForProject(
            @PathVariable Long projectId) throws ApplicationBusinessException {
        log.info("List Clients for Project method started");

        DataListResponse<DropdownResponse> response = service.listClientsByProjectId(projectId);

        log.info("List Clients for Project method finished");
        return response;
    }

    @GetMapping("/dropdown")
    @Operation(summary = "List Projects for Dropdown", description = "List Projects for dropdown with id and description")
    public DataListResponse<DropdownResponse> listForDropdown() throws ApplicationBusinessException {
        log.info("List for Dropdown method started");

        DataListResponse<DropdownResponse> response = service.listForDropdown();

        log.info("List for Dropdown method finished");
        return response;
    }

    @PostMapping
    @Operation(summary = "Create Project", description = "Create a new Project in DB")
    public DataResponse<ProjectResponse> create(@RequestBody ProjectRequest request) throws ApplicationBusinessException {
        log.info("Create method" + STARTED);
        log.debug("Create method params: {}", request);

        DataResponse<ProjectResponse> response = service.create(request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Create method" + FINISHED);
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit Project", description = "Edit an existing Project in DB")
    public DataResponse<ProjectResponse> edit(@PathVariable Long id, @RequestBody ProjectRequest request) throws ApplicationBusinessException {
        log.info("Edit method" + STARTED);
        log.debug("Edit method params: id={}, request={}", id, request);

        DataResponse<ProjectResponse> response = service.edit(id, request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Edit method" + FINISHED);
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Project", description = "Delete a project by ID")
    public void delete(@PathVariable Long id) throws ApplicationBusinessException {
        log.info("Delete method - Started");
        log.debug("Delete method params: ID {}", id);

        service.deleteById(id);

        log.info("Delete method - Finished");
    }
}
