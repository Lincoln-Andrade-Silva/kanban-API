package com.kanban.util.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataRequestTest {

    @Test
    public void testDataRequestConstructorAndGetters() {
        String testData = "test data";
        DataRequest<String> request = new DataRequest<>(testData);

        assertNotNull(request);
        assertEquals(testData, request.getData());
    }

    @Test
    public void testBuilder() {
        String testData = "test data";
        DataRequest<String> request = DataRequest.<String>builder()
                .data(testData)
                .build();

        assertNotNull(request);
        assertEquals(testData, request.getData());
    }

    @Test
    public void testEqualsAndHashCode() {
        String testData1 = "test data 1";
        String testData2 = "test data 2";
        DataRequest<String> request1 = new DataRequest<>(testData1);
        DataRequest<String> request2 = new DataRequest<>(testData1);
        DataRequest<String> request3 = new DataRequest<>(testData2);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);

        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}