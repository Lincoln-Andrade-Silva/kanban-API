package com.kanban.core.domain.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus {
    NOVO("1", "Novo"),
    EM_PROGRESSO("2", "Em Progresso"),
    EM_TESTE("3", "Em Teste"),
    FINALIZADO("4", "Finalizado");

    private final String key;
    private final String description;

    public static TaskStatus fromDescription(String description) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + description);
    }
}