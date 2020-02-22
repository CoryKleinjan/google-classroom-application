package com.kleinjan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private int courseId;
    @Column(name = "google_id")
    private String googleId;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
}
