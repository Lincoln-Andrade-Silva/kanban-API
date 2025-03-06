package com.kanban.core.domain.dto.generic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropdownResponseTest {

    @Test
    public void testDropdownResponseBuilder() {
        DropdownResponse dropdownResponse = DropdownResponse.builder()
                .id(1L)
                .description("Sample Description")
                .build();

        assertNotNull(dropdownResponse);
        assertEquals(1L, dropdownResponse.getId());
        assertEquals("Sample Description", dropdownResponse.getDescription());
    }

    @Test
    public void testDropdownResponseToString() {
        DropdownResponse dropdownResponse = DropdownResponse.builder()
                .id(1L)
                .description("Sample Description")
                .build();

        String expectedString = "DropdownResponse(id=1, description=Sample Description)";
        assertEquals(expectedString, dropdownResponse.toString());
    }
}