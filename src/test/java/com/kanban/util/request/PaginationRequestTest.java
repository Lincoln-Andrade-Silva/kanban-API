package com.kanban.util.request;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

public class PaginationRequestTest {

    @Test
    public void testConstructorWithValidArguments() {
        Integer page = 2;
        Integer pageSize = 10;
        String sortByAttribute = "name";
        Boolean ascendingOrder = true;

        PaginationRequest paginationRequest = new PaginationRequest(page, pageSize, sortByAttribute, ascendingOrder);

        assertNotNull(paginationRequest);
        assertEquals(PageRequest.of(2, 10), paginationRequest.getPage());
        assertEquals(sortByAttribute, paginationRequest.getSortByAttribute());
        assertEquals(ascendingOrder, paginationRequest.getAscendingOrder());
    }

    @Test
    public void testConstructorWithNullPageAndPageSize() {
        PaginationRequest paginationRequest = new PaginationRequest(null, null, "name", true);

        assertNotNull(paginationRequest);
        Pageable expectedPage = PageRequest.of(0, 2147483647);
        assertEquals(expectedPage, paginationRequest.getPage());
        assertEquals("name", paginationRequest.getSortByAttribute());
        assertTrue(paginationRequest.getAscendingOrder());
    }

    @Test
    public void testConstructorWithNullAscendingOrder() {
        Integer page = 1;
        Integer pageSize = 20;

        PaginationRequest paginationRequest = new PaginationRequest(page, pageSize, "name", null);

        assertNotNull(paginationRequest);
        assertEquals(PageRequest.of(1, 20), paginationRequest.getPage());
        assertEquals("name", paginationRequest.getSortByAttribute());
        assertTrue(paginationRequest.getAscendingOrder());
    }

    @Test
    public void testEqualsAndHashCode() {
        Integer page = 1;
        Integer pageSize = 10;
        String sortByAttribute = "name";
        Boolean ascendingOrder = true;

        PaginationRequest request1 = new PaginationRequest(page, pageSize, sortByAttribute, ascendingOrder);
        PaginationRequest request2 = new PaginationRequest(page, pageSize, sortByAttribute, ascendingOrder);
        PaginationRequest request3 = new PaginationRequest(2, 10, "age", false);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}