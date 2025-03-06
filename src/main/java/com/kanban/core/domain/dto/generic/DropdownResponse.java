package com.kanban.core.domain.dto.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropdownResponse {
    private Long id;
    private String description;
}