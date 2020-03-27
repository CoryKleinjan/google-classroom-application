package com.kleinjan.repository;

import com.kleinjan.model.ClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ClassGroup, Long> {
}