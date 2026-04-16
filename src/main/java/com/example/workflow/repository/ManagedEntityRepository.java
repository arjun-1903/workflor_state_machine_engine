package com.example.workflow.repository;

import com.example.workflow.model.ManagedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedEntityRepository extends JpaRepository<ManagedEntity, String> {
}
