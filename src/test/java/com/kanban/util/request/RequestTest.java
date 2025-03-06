package com.kanban.util.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestTest {

    @Test
    public void testDefaultConstructor() {
        Request request = new Request();

        assertNotNull(request);
        assertNull(request.getLocale());
    }

    @Test
    public void testAllArgsConstructor() {
        String locale = "en_US";

        Request request = new Request(locale);

        assertNotNull(request);
        assertEquals(locale, request.getLocale());
    }

    @Test
    public void testSetLocale() {
        Request request = new Request();

        String locale = "pt_BR";
        request.setLocale(locale);

        assertEquals(locale, request.getLocale());
    }

    @Test
    public void testEqualsAndHashCode() {
        Request request1 = new Request("en_US");
        Request request2 = new Request("en_US");
        Request request3 = new Request("pt_BR");

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}