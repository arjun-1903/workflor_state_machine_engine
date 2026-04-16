package com.example.workflow.controller;

import com.example.workflow.dto.CreateEntityRequest;
import com.example.workflow.dto.CreateWorkflowRequest;
import com.example.workflow.dto.TransitionRequest;
import com.example.workflow.model.ManagedEntity;
import com.example.workflow.model.TransitionHistory;
import com.example.workflow.model.Workflow;
import com.example.workflow.service.WorkflowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/workflow")
    @ResponseStatus(HttpStatus.CREATED)
    public Workflow createWorkflow(@Valid @RequestBody CreateWorkflowRequest request) {
        return workflowService.createWorkflow(request);
    }

    @GetMapping("/workflow/{id}")
    public Workflow getWorkflow(@PathVariable String id) {
        return workflowService.getWorkflow(id);
    }

    @PostMapping("/entity")
    @ResponseStatus(HttpStatus.CREATED)
    public ManagedEntity createEntity(@Valid @RequestBody CreateEntityRequest request) {
        return workflowService.createEntity(request);
    }

    @GetMapping("/entity/{id}")
    public ManagedEntity getEntity(@PathVariable String id) {
        return workflowService.getEntity(id);
    }

    @PostMapping("/entity/{id}/transition")
    public ManagedEntity transitionEntity(@PathVariable String id, @Valid @RequestBody TransitionRequest request) {
        return workflowService.transitionEntity(id, request);
    }

    @GetMapping("/entity/{id}/history")
    public List<TransitionHistory> getEntityHistory(@PathVariable String id) {
        return workflowService.getEntityHistory(id);
    }
}
