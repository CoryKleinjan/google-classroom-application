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
@Table(name = "groupings")
public class Grouping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "grouping_id")
    private int groupingId;
    @Column(name = "course_id")
    private int courseId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "grouping_id")
    private List<ClassGroup> classGroups;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "grouping_id")
    private List<Rule> rules;
}
