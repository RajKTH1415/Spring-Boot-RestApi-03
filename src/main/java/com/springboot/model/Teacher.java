package com.springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher {

    private int teacher_Id;
    private String teacherName;
    private String teacherEmail;
    private String teacherGender;
}
