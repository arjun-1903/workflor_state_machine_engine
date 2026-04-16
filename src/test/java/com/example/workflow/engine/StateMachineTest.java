package com.example.workflow.engine;

import com.example.workflow.exception.InvalidStateException;
import com.example.workflow.exception.InvalidTransitionException;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    @Test
    void shouldInitializeSuccessfullyWithValidStates() {
        assertDoesNotThrow(() -> new StateMachine(Set.of("PENDING"), Map.of()));
    }

    @Test
    void shouldThrowWhenInitializingWithEmptyStates() {
        assertThrows(IllegalArgumentException.class, () -> new StateMachine(Set.of(), Map.of()));
    }

    @Test
    void shouldThrowWhenTransitionsReferenceInvalidStates() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> 
            new StateMachine(Set.of("A"), Map.of("A", Set.of("B")))
        );
        assertTrue(ex.getMessage().contains("undefined states"));
    }

    @Test
    void shouldValidateInitialState() {
        StateMachine engine = new StateMachine(Set.of("PENDING", "APPROVED"), Map.of());
        assertDoesNotThrow(() -> engine.validateInitialState("PENDING"));
        
        InvalidStateException ex = assertThrows(InvalidStateException.class, () -> 
            engine.validateInitialState("UNKNOWN")
        );
        assertTrue(ex.getMessage().contains("not a valid state"));
    }

    @Test
    void shouldValidateAllowedTransition() {
        StateMachine engine = new StateMachine(
            Set.of("PENDING", "APPROVED"),
            Map.of("PENDING", Set.of("APPROVED"))
        );
        
        assertDoesNotThrow(() -> engine.validateTransition("PENDING", "APPROVED"));
    }

    @Test
    void shouldPreventSelfTransition() {
        StateMachine engine = new StateMachine(
            Set.of("PENDING"),
            Map.of("PENDING", Set.of("PENDING"))
        );
        
        InvalidTransitionException ex = assertThrows(InvalidTransitionException.class, () -> 
            engine.validateTransition("PENDING", "PENDING")
        );
        assertTrue(ex.getMessage().contains("Self-transition"));
    }

    @Test
    void shouldPreventUndefinedTargetStateTransition() {
        StateMachine engine = new StateMachine(Set.of("PENDING"), Map.of());
        
        InvalidStateException ex = assertThrows(InvalidStateException.class, () -> 
            engine.validateTransition("PENDING", "APPROVED")
        );
        assertTrue(ex.getMessage().contains("Target state 'APPROVED' is not defined"));
    }

    @Test
    void shouldPreventTransitionNotAllowed() {
        StateMachine engine = new StateMachine(
            Set.of("PENDING", "APPROVED", "REJECTED"),
            Map.of("PENDING", Set.of("APPROVED"))
        );
        
        InvalidTransitionException ex = assertThrows(InvalidTransitionException.class, () -> 
            engine.validateTransition("PENDING", "REJECTED")
        );
        assertTrue(ex.getMessage().contains("Transition from PENDING to REJECTED is not allowed"));
    }
}
