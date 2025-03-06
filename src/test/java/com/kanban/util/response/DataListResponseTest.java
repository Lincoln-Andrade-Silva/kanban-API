package com.kanban.util.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

public class DataListResponseTest {

    @Test
    public void testDefaultConstructor() {
        DataListResponse<String> response = new DataListResponse<>();

        assertNotNull(response);
        assertNull(response.getData());
        assertEquals(0, response.getTotalPages());
        assertEquals(0L, response.getTotalElements());
    }

    @Test
    public void testAllArgsConstructor() {
        DataListResponse<String> response = new DataListResponse<>(
                Collections.singletonList("Item1"),
                5,
                10L
        );

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals("Item1", response.getData().get(0));
        assertEquals(5, response.getTotalPages());
        assertEquals(10L, response.getTotalElements());
    }

    @Test
    public void testSetData() {
        DataListResponse<String> response = new DataListResponse<>();
        response.setData(Collections.singletonList("Item1"));

        assertEquals(1, response.getData().size());
        assertEquals("Item1", response.getData().get(0));
    }

    @Test
    public void testSetTotalPages() {
        DataListResponse<String> response = new DataListResponse<>();
        response.setTotalPages(3);

        assertEquals(3, response.getTotalPages());
    }

    @Test
    public void testSetTotalElements() {
        DataListResponse<String> response = new DataListResponse<>();
        response.setTotalElements(100L);

        assertEquals(100L, response.getTotalElements());
    }

    @Test
    public void testEqualsAndHashCode() {
        DataListResponse<String> response1 = new DataListResponse<>(Collections.singletonList("Item1"), 5, 10L);
        DataListResponse<String> response2 = new DataListResponse<>(Collections.singletonList("Item1"), 5, 10L);
        DataListResponse<String> response3 = new DataListResponse<>(Collections.singletonList("Item2"), 6, 15L);

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}