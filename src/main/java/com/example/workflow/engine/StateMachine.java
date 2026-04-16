package com.example.workflow.engine;

import com.example.workflow.exception.InvalidStateException;
import com.example.workflow.exception.InvalidTransitionException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class StateMachine {
    private final Set<String> validStates;
    private final Map<String, Set<String>> allowedTransitions;

    public StateMachine(Set<String> validStates, Map<String, Set<String>> allowedTransitions) {
        // Defensive validation at startup/load
        if (validStates == null || validStates.isEmpty()) {
            throw new IllegalArgumentException("Workflow must have at least one valid state defined.");
        }
        if (allowedTransitions != null) {
            allowedTransitions.forEach((from, toList) -> {
                if (!validStates.contains(from) || !validStates.containsAll(toList)) {
                    throw new IllegalArgumentException("Workflow transitions reference undefined states.");
                }
            });
        }
        
        this.validStates = validStates;
        this.allowedTransitions = allowedTransitions != null ? allowedTransitions : Collections.emptyMap();
    }

    public void validateInitialState(String state) {
        if (!validStates.contains(state)) {
            throw new InvalidStateException("Initial state '" + state + "' is not a valid state for this workflow.");
        }
    }

    public void validateTransition(String fromState, String toState) {
         if (fromState.equals(toState)) {
             throw new InvalidTransitionException("Self-transition from " + fromState + " to " + toState + " is not allowed.");
         }
         if (!validStates.contains(toState)) {
             throw new InvalidStateException("Target state '" + toState + "' is not defined in this workflow.");
         }
         
         Set<String> validNextStates = allowedTransitions.getOrDefault(fromState, Collections.emptySet());
         if (!validNextStates.contains(toState)) {
             throw new InvalidTransitionException("Transition from " + fromState + " to " + toState + " is not allowed.");
         }
    }
    
    public Set<String> getValidStates() {
        return validStates;
    }
    
    public Map<String, Set<String>> getAllowedTransitions() {
        return allowedTransitions;
    }
}
