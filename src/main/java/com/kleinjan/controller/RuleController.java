package com.kleinjan.controller;


import com.kleinjan.model.Rule;
import com.kleinjan.returnWrappers.RuleReturn;
import com.kleinjan.service.RuleService;
import com.kleinjan.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

    @Autowired
    RuleService ruleService;

    @RequestMapping("/delete-rule")
    public void deleteRule(@RequestParam Integer ruleId){
        ruleService.deleteById(ruleId);
    }

    @RequestMapping("/update-rule")
    public void updateRule(@RequestBody RuleReturn ruleReturn){
        Rule rule = new Rule();
        rule.setId(ruleReturn.getRuleId());
        rule.setType(ruleReturn.getRuleType());
        rule.setFirstStudent(ruleReturn.getFirstStudentId());
        rule.setSecondStudent(ruleReturn.getSecondStudentId());

        ruleService.save(rule);
    }
}
