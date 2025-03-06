package com.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.service.client.IClientService;
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

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IClientService service;

    private ClientRequest clientRequest;
    private DataListResponse<ClientResponse> clientListResponse;
    private DataResponse<ClientResponse> clientDataResponse;
    private DataListResponse<DropdownResponse> dropdownResponse;

    @BeforeEach
    void setUp() {
        clientRequest = ClientRequest.builder()
                .name("Test Client")
                .build();

        ClientResponse clientResponse = ClientResponse.builder()
                .id(1L)
                .name("Test Client")
                .build();

        clientDataResponse = new DataResponse<>();
        clientDataResponse.setData(clientResponse);

        clientListResponse = new DataListResponse<>(Collections.singletonList(clientResponse), 0, 1L);

        dropdownResponse = new DataListResponse<>(List.of(new DropdownResponse(1L, "Test Client")), 0, 1L);
    }

    @Test
    void testList() throws Exception {
        when(service.list(1, 10)).thenReturn(clientListResponse);

        mockMvc.perform(get("/client?page=1&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testListClientsForDropdown() throws Exception {
        when(service.listForDropdown()).thenReturn(dropdownResponse);

        mockMvc.perform(get("/client/dropdown"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        when(service.create(clientRequest)).thenReturn(clientDataResponse);

        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content("{\"name\": \"Test Client\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testEdit() throws Exception {
        when(service.edit(1L, clientRequest)).thenReturn(clientDataResponse);
        String jsonRequest = new ObjectMapper().writeValueAsString(clientRequest);

        mockMvc.perform(put("/client/1")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Operation concluded with success"));
    }

    @Test
    void testDelete() throws Exception {
        Mockito.doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/client/1"))
                .andExpect(status().isOk());
    }
}