package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.Student;

public interface Studentrepository extends JpaRepository<Student, Long> {

	boolean existsByStudentEmail(String studentEmail);

}
