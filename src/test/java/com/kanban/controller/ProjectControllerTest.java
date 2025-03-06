package com.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanban.core.domain.dto.project.ProjectRequest;
import com.kanban.core.domain.dto.project.ProjectResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.service.project.IProjectService;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProjectService service;

    private ProjectRequest projectRequest;
    private DataListResponse<ProjectResponse> projectListResponse;
    private DataResponse<ProjectResponse> projectDataResponse;
    private DataListResponse<DropdownResponse> dropdownResponse;

    @BeforeEach
    void setUp() {
        projectRequest = ProjectRequest.builder()
                .name("Test Project")
                .build();

        ProjectResponse projectResponse = ProjectResponse.builder()
                .id(1L)
                .name("Test Project")
                .build();

        projectDataResponse = new DataResponse<>();
        projectDataResponse.setData(projectResponse);

        projectListResponse = new DataListResponse<>(Collections.singletonList(projectResponse), 0, 1L);

        dropdownResponse = new DataListResponse<>(List.of(new DropdownResponse(1L, "Test Project")), 0, 1L);
    }

    @Test
    void testList() throws Exception {
        when(service.list(1, 10)).thenReturn(projectListResponse);

        mockMvc.perform(get("/project?page=1&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testListClientsForProject() throws Exception {
        when(service.listClientsByProjectId(1L)).thenReturn(dropdownResponse);

        mockMvc.perform(get("/project/1/clients"))
                .andExpect(status().isOk());
    }

    @Test
    void testListForDropdown() throws Exception {
        when(service.listForDropdown()).thenReturn(dropdownResponse);

        mockMvc.perform(get("/project/dropdown"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(projectRequest)).thenReturn(projectDataResponse);

        String jsonRequest = new ObjectMapper().writeValueAsString(projectRequest);

        mockMvc.perform(post("/project")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testEdit() throws Exception {
        when(service.edit(1L, projectRequest)).thenReturn(projectDataResponse);
        String jsonRequest = new ObjectMapper().writeValueAsString(projectRequest);

        mockMvc.perform(put("/project/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/project/1"))
                .andExpect(status().isOk());
    }
}