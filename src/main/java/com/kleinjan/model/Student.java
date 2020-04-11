package com.kleinjan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student implements Comparable<Student>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id")
    private int studentId;
    @Column(name = "google_id")
    private String googleId;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
    @ManyToMany(mappedBy = "students")
    private List<ClassGroup> classGroups;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "student_id")
    private List<Assignment> assignments;

    @Override
    public int compareTo(Student second){
        List<Assignment> firstAssignments = getAssignments();
        List<Assignment> secondAssignments = second.getAssignments();

        Double firstGrade = 0.0;
        Double secondGrade = 0.0;

        for(Assignment assignment : firstAssignments){
            firstGrade += assignment.getGrade();
        }
        for(Assignment assignment : secondAssignments){
            secondGrade += assignment.getGrade();
        }

        return firstGrade.compareTo(secondGrade);
    }
}
