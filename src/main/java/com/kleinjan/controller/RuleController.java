package com.kleinjan.controller;


import com.kleinjan.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
