package com.example.workflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entities")
public class ManagedEntity {

    @Id
    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "workflow_id", nullable = false)
    private String workflowId;

    @Column(name = "current_state", nullable = false)
    private String currentState;

    public ManagedEntity() {}

    public ManagedEntity(String entityId, String workflowId, String currentState) {
        this.entityId = entityId;
        this.workflowId = workflowId;
        this.currentState = currentState;
    }

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

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }
}
