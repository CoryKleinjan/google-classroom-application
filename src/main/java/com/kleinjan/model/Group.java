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
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "grouping_id")
    private String groupingId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_students", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
}
