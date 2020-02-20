package com.kleinjan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username")
    @Length(min = 4, message = "*Your user name must have at least 4 characters")
    @NotEmpty(message = "*You must provide a user name")
    private String userName;
    @Column(name = "email")
    @Email(message = "*You must provide a valid Email")
    @NotEmpty(message = "*You must provide a Email")
    private String email;
    @Column(name = "password")
    @Length(min = 4, message = "*Your password must have at least 4 characters")
    @NotEmpty(message = "*You must provide a password")
    private String password;
    @Column(name = "first_name")
    @NotEmpty(message = "*Please enter your First Name")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Please enter your Last Name")
    private String lastName;
    @Column(name = "enabled")
    private Boolean enabled;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
