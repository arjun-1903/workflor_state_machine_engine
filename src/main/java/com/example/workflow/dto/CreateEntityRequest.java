package com.example.workflow.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateEntityRequest {
    @NotBlank
    private String entityId;

    @NotBlank
    private String workflowId;

    @NotBlank
    private String initialState;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }
}
