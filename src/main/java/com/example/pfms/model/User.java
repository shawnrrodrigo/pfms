package com.example.pfms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    private String role;
    private boolean active;
    @OneToMany(mappedBy = "user")
    private List<Income> incomes;
    @OneToMany(mappedBy = "user")
    private List<Expense> expenses;
    @OneToMany(mappedBy = "user")
    private List<Budget> budgets;

}
