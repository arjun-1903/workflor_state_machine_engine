package com.example.workflow.service;

import com.example.workflow.dto.CreateEntityRequest;
import com.example.workflow.dto.CreateWorkflowRequest;
import com.example.workflow.dto.TransitionRequest;
import com.example.workflow.engine.StateMachine;
import com.example.workflow.model.ManagedEntity;
import com.example.workflow.model.TransitionHistory;
import com.example.workflow.model.Workflow;
import com.example.workflow.repository.ManagedEntityRepository;
import com.example.workflow.repository.TransitionHistoryRepository;
import com.example.workflow.repository.WorkflowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final ManagedEntityRepository entityRepository;
    private final TransitionHistoryRepository historyRepository;

    public WorkflowService(WorkflowRepository workflowRepository,
                           ManagedEntityRepository entityRepository,
                           TransitionHistoryRepository historyRepository) {
        this.workflowRepository = workflowRepository;
        this.entityRepository = entityRepository;
        this.historyRepository = historyRepository;
    }

    @Transactional
    public Workflow createWorkflow(CreateWorkflowRequest request) {
        // Validation happens in constructor
        new StateMachine(request.getConfig().getStates(), request.getConfig().getTransitions());
        
        Workflow workflow = new Workflow(request.getName(), request.getVersion(), request.getConfig());
        return workflowRepository.save(workflow);
    }

    public Workflow getWorkflow(String id) {
        return workflowRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Workflow not found with ID: " + id));
    }

    @Transactional
    public ManagedEntity createEntity(CreateEntityRequest request) {
        if (entityRepository.existsById(request.getEntityId())) {
            throw new IllegalArgumentException("Entity already exists with ID: " + request.getEntityId());
        }

        Workflow workflow = getWorkflow(request.getWorkflowId());
        StateMachine engine = new StateMachine(workflow.getConfig().getStates(), workflow.getConfig().getTransitions());
        
        engine.validateInitialState(request.getInitialState());

        ManagedEntity entity = new ManagedEntity(request.getEntityId(), request.getWorkflowId(), request.getInitialState());
        return entityRepository.save(entity);
    }

    @Transactional
    public ManagedEntity transitionEntity(String entityId, TransitionRequest request) {
        ManagedEntity entity = entityRepository.findById(entityId)
                .orElseThrow(() -> new IllegalStateException("Entity not found with ID: " + entityId));

        Workflow workflow = getWorkflow(entity.getWorkflowId());
        StateMachine engine = new StateMachine(workflow.getConfig().getStates(), workflow.getConfig().getTransitions());

        String fromState = entity.getCurrentState();
        String toState = request.getToState();

        engine.validateTransition(fromState, toState);

        // Validated, now apply change
        entity.setCurrentState(toState);
        entityRepository.save(entity);

        // Record history
        TransitionHistory history = new TransitionHistory(entityId, fromState, toState, request.getPerformedBy());
        historyRepository.save(history);

        return entity;
    }

    public ManagedEntity getEntity(String entityId) {
        return entityRepository.findById(entityId)
                .orElseThrow(() -> new IllegalStateException("Entity not found with ID: " + entityId));
    }

    public List<TransitionHistory> getEntityHistory(String entityId) {
        return historyRepository.findByEntityIdOrderByTimestampAsc(entityId);
    }
}
