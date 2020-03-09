package com.kleinjan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignment_id")
    private int assignmentId;
    @Column(name = "course_id")
    private int courseId;
    @Column(name = "grade")
    private Double grade;
    @Column(name = "total_points")
    private Double totalPoints;
    @Column(name = "student_id")
    private int studentId;
}
