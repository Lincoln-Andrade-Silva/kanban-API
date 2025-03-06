package com.kanban.core.domain.validator;

import com.kanban.util.exception.ApplicationBusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testValidatePageNotEmptyThrowsExceptionWhenPageIsEmpty() {
        Page<Object> emptyPage = Page.empty();

        Exception exception = assertThrows(ApplicationBusinessException.class, () ->
                Validator.validatePageNotEmpty(emptyPage, "No data found"));

        ApplicationBusinessException businessException = (ApplicationBusinessException) exception;
        assertEquals(HttpStatus.NO_CONTENT.value(), businessException.getHttpStatusCode());
        assertEquals("Sem conteúdo", businessException.getTitle());
        assertEquals("No data found", businessException.getMessage());
    }

    @Test
    void testValidatePageNotEmptyDoesNotThrowExceptionWhenPageIsNotEmpty() {
        Page<Object> nonEmptyPage = new PageImpl<>(List.of(new Object()));

        assertDoesNotThrow(() -> Validator.validatePageNotEmpty(nonEmptyPage, "Data found"));
    }

    @Test
    void testValidateIfExistWithSameFieldThrowsExceptionWhenEntityExists() {
        Optional<Object> existingEntity = Optional.of(new Object());

        Exception exception = assertThrows(ApplicationBusinessException.class, () ->
                Validator.validateIfExistWithSameField(existingEntity, "field", "Entity"));

        ApplicationBusinessException businessException = (ApplicationBusinessException) exception;
        assertEquals("Entity já existe", businessException.getTitle());
        assertEquals("Entity com o mesmo field já existe", businessException.getMessage());
    }

    @Test
    void testValidateIfExistWithSameFieldDoesNotThrowExceptionWhenEntityDoesNotExist() {
        Optional<Object> absentEntity = Optional.empty();

        assertDoesNotThrow(() -> Validator.validateIfExistWithSameField(absentEntity, "field", "Entity"));
    }
}