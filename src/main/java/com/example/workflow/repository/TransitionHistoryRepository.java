package com.example.workflow.repository;

import com.example.workflow.model.TransitionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitionHistoryRepository extends JpaRepository<TransitionHistory, Long> {
    List<TransitionHistory> findByEntityIdOrderByTimestampAsc(String entityId);
}
