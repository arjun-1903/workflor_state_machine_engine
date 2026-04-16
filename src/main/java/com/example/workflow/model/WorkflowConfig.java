package com.example.workflow.model;

import java.util.Map;
import java.util.Set;

public class WorkflowConfig {
    private Set<String> states;
    private Map<String, Set<String>> transitions;

    public WorkflowConfig() {}

    public WorkflowConfig(Set<String> states, Map<String, Set<String>> transitions) {
        this.states = states;
        this.transitions = transitions;
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public Map<String, Set<String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Set<String>> transitions) {
        this.transitions = transitions;
    }
}
