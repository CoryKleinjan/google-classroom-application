package com.kleinjan.repository;

import com.kleinjan.model.Assignment;
import com.kleinjan.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {
    public void deleteById(Integer ruleId);
}
