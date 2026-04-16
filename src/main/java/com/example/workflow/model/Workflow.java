package com.example.workflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "workflows")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer version;

    @Convert(converter = WorkflowConfigConverter.class)
    @Column(name = "config_json", columnDefinition = "TEXT", nullable = false)
    private WorkflowConfig config;

    public Workflow() {}

    public Workflow(String name, Integer version, WorkflowConfig config) {
        this.name = name;
        this.version = version;
        this.config = config;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
