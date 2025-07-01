package com.springboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "STUDENT-TABLE")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;


	@NotBlank(message = "Name is mandatory")
	private String studentName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email format")
	private String studentEmail;

	@NotBlank(message = "Gender is mandatory")
	private String studentGender;
	
}
