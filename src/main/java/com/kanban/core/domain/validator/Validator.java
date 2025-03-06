package com.kanban.core.domain.validator;

import com.kanban.util.exception.ApplicationBusinessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class Validator {

    private Validator() {
    }

    public static void validatePageNotEmpty(Page<?> page, String message) throws ApplicationBusinessException {
        if (page.isEmpty()) {
            throw new ApplicationBusinessException(
                    HttpStatus.NO_CONTENT.value(),
                    "Sem conteúdo",
                    message,
                    ""
            );
        }
    }

    public static <T> void validateIfExistWithSameField(Optional<T> optional, String field, String entityName) throws ApplicationBusinessException {
        if (optional.isPresent()) {
            throw new ApplicationBusinessException(
                    entityName + " já existe",
                    entityName + " com o mesmo " + field + " já existe"
            );
        }
    }
}