package com.kanban.util.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    public void testDefaultConstructor() {
        Response response = new Response();

        assertNotNull(response);
        assertNull(response.getInput());
        assertNull(response.getMessage());
    }

    @Test
    public void testSettersAndGetters() {
        Response response = new Response();
        String input = "Test Input";
        String message = "Test Message";

        response.setInput(input);
        response.setMessage(message);

        assertEquals(input, response.getInput());
        assertEquals(message, response.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        Response response1 = new Response();
        response1.setInput("Input1");
        response1.setMessage("Message1");

        Response response2 = new Response();
        response2.setInput("Input1");
        response2.setMessage("Message1");

        Response response3 = new Response();
        response3.setInput("Input2");
        response3.setMessage("Message2");

        assertEquals(response1, response2);
        assertNotEquals(response1, response3);

        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    public void testJsonIncludeAnnotation() {
        Response response = new Response();
        response.setInput(null);
        response.setMessage("Message");

        assertNotNull(response);
        assertNull(response.getInput());
        assertEquals("Message", response.getMessage());
    }
}
