package com.kleinjan.service;

import com.kleinjan.model.ClassGroup;
import com.kleinjan.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public ClassGroup save(ClassGroup classGroup) {
        return groupRepository.save(classGroup);
    }

    public void deleteById(Integer groupId) { groupRepository.deleteById(groupId);}

    public ClassGroup getById(Integer groupId) {
        return groupRepository.getOne(groupId);
    }
}
