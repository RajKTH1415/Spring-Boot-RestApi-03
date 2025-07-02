package com.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacher_Id;

    @NotBlank(message = "Name is mandatory")
    private String teacherName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String teacherEmail;

    @NotBlank(message = "Gender is mandatory")
    private String teacherGender;
}
