package com.kanban.util.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterRequestTest {

    @Test
    public void testFilterRequestConstructorAndGetters() {
        String testData = "test data";
        String locale = "en";
        String strSearch = "search query";

        FilterRequest<String> request = new FilterRequest<>(testData, locale, strSearch);

        assertNotNull(request);
        assertEquals(testData, request.getData());
        assertEquals(locale, request.getLocale());
        assertEquals(strSearch, request.getStrSearch());
    }

    @Test
    public void testBuilder() {
        String testData = "test data";
        String locale = "en";
        String strSearch = "search query";

        FilterRequest<String> request = FilterRequest.<String>builder()
                .data(testData)
                .locale(locale)
                .strSearch(strSearch)
                .build();

        assertNotNull(request);
        assertEquals(testData, request.getData());
        assertEquals(locale, request.getLocale());
        assertEquals(strSearch, request.getStrSearch());
    }

    @Test
    public void testEqualsAndHashCode() {
        String testData1 = "test data 1";
        String testData2 = "test data 2";
        String locale = "en";
        String strSearch = "search query";

        FilterRequest<String> request1 = new FilterRequest<>(testData1, locale, strSearch);
        FilterRequest<String> request2 = new FilterRequest<>(testData1, locale, strSearch);
        FilterRequest<String> request3 = new FilterRequest<>(testData2, locale, strSearch);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}