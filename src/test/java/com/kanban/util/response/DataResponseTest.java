package com.kanban.util.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataResponseTest {

    @Test
    public void testDefaultConstructor() {
        DataResponse<String> response = new DataResponse<>();

        assertNotNull(response);
        assertNull(response.getData());
        assertNull(response.getMessage());
    }

    @Test
    public void testAllArgsConstructor() {
        String data = "Sample Data";
        DataResponse<String> response = new DataResponse<>(data);

        assertNotNull(response);
        assertEquals(data, response.getData());
        assertNull(response.getMessage());
    }

    @Test
    public void testConstructorWithMessage() {
        String data = "Sample Data";
        String message = "Success";
        DataResponse<String> response = new DataResponse<>(data, message);

        assertNotNull(response);
        assertEquals(data, response.getData());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testSetData() {
        DataResponse<String> response = new DataResponse<>();
        String data = "Updated Data";
        response.setData(data);

        assertEquals(data, response.getData());
    }

    @Test
    public void testSetMessage() {
        DataResponse<String> response = new DataResponse<>();
        String message = "Success";
        response.setMessage(message);

        assertEquals(message, response.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        DataResponse<String> response1 = new DataResponse<>("Item1", "Success");
        DataResponse<String> response2 = new DataResponse<>("Item1", "Success");
        DataResponse<String> response3 = new DataResponse<>("Item2", "Error");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
}