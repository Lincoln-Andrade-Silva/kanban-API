package com.kanban.core.domain.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMessagesTest {

    @Test
    public void testConstants() {
        assertEquals("Tarefa não encontrada", TaskMessages.TASK_NOT_FOUND);
        assertEquals("A tarefa com ID %d não foi encontrada", TaskMessages.TASK_NOT_FOUND_BY_ID);
        assertEquals("Projeto associado à tarefa não encontrado", TaskMessages.TASK_PROJECT_NOT_FOUND);
        assertEquals("O projeto com ID %d não foi encontrado", TaskMessages.TASK_PROJECT_NOT_FOUND_BY_ID);
    }
}