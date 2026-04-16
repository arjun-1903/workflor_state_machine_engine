package com.example.workflow.dto;

import jakarta.validation.constraints.NotBlank;

public class TransitionRequest {
    @NotBlank
    private String toState;

    private String performedBy;

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }
}
