package com.kanban.core.domain.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String name;
    private String status;
    private Long projectId;
    private Long clientId;
}