package com.kleinjan.repository;

import com.kleinjan.model.Grouping;
import com.kleinjan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupingRepository extends JpaRepository<Grouping, Integer> {

    public List<Grouping> findByCourseId(Integer courseId);

}
