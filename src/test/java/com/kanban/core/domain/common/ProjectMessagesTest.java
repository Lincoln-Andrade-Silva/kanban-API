package com.kanban.core.domain.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectMessagesTest {

    @Test
    public void testConstants() {
        assertEquals("Não existem projetos cadastrados", ProjectMessages.NO_PROJECTS_FOUND);
        assertEquals("Operação realizada com sucesso", ProjectMessages.SUCCESSFUL_OPERATION);
        assertEquals("Projeto não encontrado", ProjectMessages.PROJECT_NOT_FOUND);
        assertEquals("O projeto com ID %d não foi encontrado", ProjectMessages.PROJECT_NOT_FOUND_BY_ID);
    }
}