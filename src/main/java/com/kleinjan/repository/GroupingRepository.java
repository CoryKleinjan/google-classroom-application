package com.kleinjan.repository;

import com.kleinjan.model.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupingRepository extends JpaRepository<Grouping, Long> {
}
