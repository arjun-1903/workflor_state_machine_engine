package com.example.workflow.dto;

import com.example.workflow.model.WorkflowConfig;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateWorkflowRequest {
    @NotBlank
    private String name;
    
    // Default version is 1 if not provided
    private Integer version = 1;

    @NotNull
    private WorkflowConfig config;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public WorkflowConfig getConfig() {
        return config;
    }

    public void setConfig(WorkflowConfig config) {
        this.config = config;
    }
}
