package com.kleinjan.service;

import com.kleinjan.repository.GroupingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupingService {

    private GroupingRepository groupingRepository;

    @Autowired
    public GroupingService(GroupingRepository groupingRepository) {
        this.groupingRepository = groupingRepository;
    }
}
