package com.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentEmail(String studentEmail);

    List<Student> findByStudentGenderIgnoreCase(String gender);
}
