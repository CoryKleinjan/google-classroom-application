package com.kleinjan.repository;

import com.kleinjan.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Assignment, Long> {
}
