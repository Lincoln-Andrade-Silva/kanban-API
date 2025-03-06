package com.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanban.core.domain.dto.task.TaskRequest;
import com.kanban.core.domain.dto.task.TaskResponse;
import com.kanban.core.service.task.ITaskService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITaskService service;

    private TaskRequest taskRequest;
    private DataListResponse<TaskResponse> taskListResponse;
    private DataResponse<TaskResponse> taskDataResponse;

    @BeforeEach
    void setUp() {
        taskRequest = TaskRequest.builder()
                .name("Test Task")
                .projectId(1L)
                .build();

        TaskResponse taskResponse = TaskResponse.builder()
                .id(1L)
                .name("Test Task")
                .projectId(1L)
                .build();

        taskDataResponse = new DataResponse<>();
        taskDataResponse.setData(taskResponse);

        taskListResponse = new DataListResponse<>(Collections.singletonList(taskResponse), 0, 1L);
    }

    @Test
    void testList() throws Exception {
        when(service.list(1)).thenReturn(taskListResponse);

        mockMvc.perform(get("/task?projectId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(taskRequest)).thenReturn(taskDataResponse);

        String jsonRequest = new ObjectMapper().writeValueAsString(taskRequest);

        mockMvc.perform(post("/task")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testEdit() throws Exception {
        when(service.edit(1L, taskRequest)).thenReturn(taskDataResponse);
        String jsonRequest = new ObjectMapper().writeValueAsString(taskRequest);

        mockMvc.perform(put("/task/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk());
    }
}