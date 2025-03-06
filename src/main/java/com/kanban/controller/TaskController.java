package com.kanban.controller;

import com.kanban.core.domain.common.DomainReturnCode;
import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.service.task.ITaskService;
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
@RequestMapping(value = "task")
@CrossOrigin(origins = "${api.access.control.allow.origin}")
@Tag(name = "Task Controller", description = "Endpoints of Task Controller")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private static final String STARTED = " - Started";
    private static final String FINISHED = " - Finished";

    private final ITaskService service;

    @GetMapping
    @Operation(summary = "List Tasks", description = "List All Tasks by Project Id from DB")
    public DataListResponse<TaskResponse> list(@RequestParam(name = "projectId") Integer projectId)
            throws ApplicationBusinessException {
        log.info("List method" + STARTED);
        log.debug("List method params, Project Id:{}", projectId);

        DataListResponse<TaskResponse> response = service.list(projectId);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("List method" + FINISHED);
        return response;
    }

    @PostMapping
    @Operation(summary = "Create Task", description = "Create a new Task")
    public DataResponse<TaskResponse> create(@RequestBody TaskRequest request)
            throws ApplicationBusinessException {
        log.info("Create method" + STARTED);
        log.debug("Create method params: {}", request);

        DataResponse<TaskResponse> response = service.create(request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Create method" + FINISHED);
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit Task", description = "Edit an existing Task")
    public DataResponse<TaskResponse> edit(@PathVariable Long id, @RequestBody TaskRequest request)
            throws ApplicationBusinessException {
        log.info("Edit method" + STARTED);
        log.debug("Edit method params, ID: {}, Request: {}", id, request);

        DataResponse<TaskResponse> response = service.edit(id, request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Edit method" + FINISHED);
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task", description = "Delete a Task by ID")
    public void delete(@PathVariable Long id) throws ApplicationBusinessException {
        log.info("Delete method" + STARTED);
        log.debug("Delete method params, ID: {}", id);

        service.delete(id);

        log.info("Delete method" + FINISHED);
    }
}