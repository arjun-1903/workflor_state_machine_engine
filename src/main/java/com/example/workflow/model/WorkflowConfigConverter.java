package com.example.workflow.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class WorkflowConfigConverter implements AttributeConverter<WorkflowConfig, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(WorkflowConfig config) {
        if (config == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(config);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing workflow config to JSON", e);
        }
    }

    @Override
    public WorkflowConfig convertToEntityAttribute(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, WorkflowConfig.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing workflow config from JSON", e);
        }
    }
}
