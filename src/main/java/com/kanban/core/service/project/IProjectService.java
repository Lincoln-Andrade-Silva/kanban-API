package com.kanban.core.service.project;

import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;

import java.util.List;

public interface IProjectService {
    DataListResponse<ProjectResponse> list(Integer page, Integer pageSize) throws ApplicationBusinessException;

    DataListResponse<DropdownResponse> listForDropdown() throws ApplicationBusinessException;

    DataResponse<ProjectResponse> create(ProjectRequest request) throws ApplicationBusinessException;

    DataResponse<ProjectResponse> edit(Long projectId, ProjectRequest request) throws ApplicationBusinessException;

    void deleteById(Long id) throws ApplicationBusinessException;

    DataListResponse<DropdownResponse> listClientsByProjectId(Long projectId) throws ApplicationBusinessException;
}
