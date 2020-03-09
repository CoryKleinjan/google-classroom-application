package com.kleinjan.service;

import com.kleinjan.model.Grouping;
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

    public Grouping save(Grouping grouping) {
        return groupingRepository.save(grouping);
    }
}
