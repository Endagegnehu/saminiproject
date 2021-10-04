package com.example.demosecurity.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    private String first;
    private String last;
    private double gpa;
    private LocalDate age;

    public Person(String firstName, String lastName, double gpa, LocalDate age) {
        this.first = firstName;
        this.last = lastName;
        this.gpa = gpa;
        this.age = age;
    }
}
