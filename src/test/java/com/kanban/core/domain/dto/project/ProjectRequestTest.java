package com.kanban.core.domain.dto.project;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectRequestTest {

    @Test
    public void testProjectRequestBuilder() {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("Project A")
                .status("Active")
                .clientIds(Arrays.asList(1L, 2L, 3L))
                .build();

        assertNotNull(projectRequest);
        assertEquals("Project A", projectRequest.getName());
        assertEquals("Active", projectRequest.getStatus());
        assertEquals(Arrays.asList(1L, 2L, 3L), projectRequest.getClientIds());
    }

    @Test
    public void testProjectRequestToString() {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("Project A")
                .status("Active")
                .clientIds(Arrays.asList(1L, 2L, 3L))
                .build();

        String expectedString = "ProjectRequest = { name: Project A, status: Active, clientIds: 1,2,3 }";
        assertEquals(expectedString, projectRequest.toString());
    }

    @Test
    public void testProjectRequestToStringWithEmptyClientIds() {
        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("Project A")
                .status("Active")
                .clientIds(null)
                .build();

        String expectedString = "ProjectRequest = { name: Project A, status: Active, clientIds: [] }";
        assertEquals(expectedString, projectRequest.toString());
    }
}