package com.kanban.core.domain.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatus {
    EM_ANDAMENTO("1", "Em Andamento"),
    CANCELADO("2", "Cancelado"),
    FINALIZADO("3", "Finalizado");

    private final String key;
    private final String description;

    public static ProjectStatus fromDescription(String description) {
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + description);
    }
}