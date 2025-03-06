package com.kanban.core.service.task;

import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;

public interface ITaskService {
    DataListResponse<TaskResponse> list(Integer projectId);

    DataResponse<TaskResponse> create(TaskRequest request) throws ApplicationBusinessException;

    DataResponse<TaskResponse> edit(Long taskId, TaskRequest request) throws ApplicationBusinessException;

    void delete(Long taskId) throws ApplicationBusinessException;
}
