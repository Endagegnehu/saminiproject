package com.example.demosecurity.batchJob;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PersonDTO {
    private String first;
    private String last;
    double gpa;
    int age;
}
