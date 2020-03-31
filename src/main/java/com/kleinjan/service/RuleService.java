package com.kleinjan.service;

import com.kleinjan.model.Rule;
import com.kleinjan.model.Student;
import com.kleinjan.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    private RuleRepository ruleRepository;

    @Autowired
    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }
}
