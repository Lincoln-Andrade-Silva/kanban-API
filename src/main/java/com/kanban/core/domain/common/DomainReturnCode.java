package com.kanban.core.domain.common;

public enum DomainReturnCode {

    /*        Operations     */
    SUCCESSFUL_OPERATION("Operation concluded with success");

    private final String description;

    DomainReturnCode(String value) {
        description = value;
    }

    public String getDesc() {
        return description;
    }
}