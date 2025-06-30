package com.springboot.repository;

import com.springboot.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private Studentrepository studentrepository;

    @Test
    @DisplayName("Should return true if student email exists")
    void testExistsByStudentEmail_ReturnsTrue() {

        Student student = new Student();
        student.setStudentName("Raj");
        student.setStudentEmail("raj@gmail.com");
        student.setStudentGender("Male");
        studentrepository.save(student);


        boolean exists = studentrepository.existsByStudentEmail("raj@gmail.com");


        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false if student email does not exist")
    void testExistsByStudentEmail_ReturnsFalse() {

        boolean exists = studentrepository.existsByStudentEmail("notfound@gmail.com");
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should save and retrieve student by ID")
    void testSaveAndFindById() {

        Student student = new Student();
        student.setStudentName("Kumar");
        student.setStudentEmail("kumar@gmail.com");
        student.setStudentGender("Male");

        Student saved = studentrepository.save(student);


        Student found = studentrepository.findById(saved.getStudentId()).orElse(null);


        assertThat(found).isNotNull();
        assertThat(found.getStudentEmail()).isEqualTo("kumar@gmail.com");
    }

    @Test
    @DisplayName("Should return all saved students")
    void testFindAll() {

        Student s1 = new Student(null, "Alice", "alice@gmail.com", "Female");
        Student s2 = new Student(null, "Bob", "bob@gmail.com", "Male");
        Student s3 = new Student(null, "Komal", "komal@gmail.com", "Female");
        Student s4 = new Student(null, "Anjali", "rani@gmail.com", "Female");


        studentrepository.save(s1);
        studentrepository.save(s2);
        studentrepository.save(s3);
        studentrepository.save(s4);


        var students = studentrepository.findAll();
        assertThat(students).hasSize(4);
    }
}
