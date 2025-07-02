package com.springboot.repository;

import com.springboot.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    boolean existsByTeacherEmail(String teacherEmail);

}
