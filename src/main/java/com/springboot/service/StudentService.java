package com.springboot.service;

import com.springboot.exception.DuplicateFoundException;
import com.springboot.exception.NoDataAvailableException;
import com.springboot.exception.StudentNotFoundException;
import com.springboot.model.Student;
import com.springboot.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentrepository;

    public StudentService(StudentRepository studentrepository) {
        this.studentrepository = studentrepository;
    }

    public Student createStudent(Student student) {
        logger.info("Attempting to create student with email: {}", student.getStudentEmail());
        validateDuplicateEmail(student.getStudentEmail());
        Student savedStudent = studentrepository.save(student);
        logger.info("Student created successfully with ID: {}", savedStudent.getStudentId());
        return savedStudent;
    }

    public Student getStudentById(Long studentId) {
        logger.debug("Fetching student with ID: {}", studentId);
        return findStudentOrThrow(studentId);
    }

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        List<Student> students = studentrepository.findAll();
        validateStudentsNotEmpty(students);
        logger.debug("Found {} students", students.size());
        return students;
    }

    public Student updateStudent(Long studentId, Student student) {
        logger.info("Updating student with ID: {}", studentId);
        Student existingStudent = findStudentOrThrow(studentId);
        existingStudent.setStudentName(student.getStudentName());
        existingStudent.setStudentGender(student.getStudentGender());
        existingStudent.setStudentEmail(student.getStudentEmail());
        logger.debug("Student details updated for ID: {}", studentId);
        return studentrepository.save(existingStudent);
    }

    public Student deleteStudentById(Long studentId) {
        logger.info("Deleting student with ID: {}", studentId);
        Student student = findStudentOrThrow(studentId);
        studentrepository.deleteById(studentId);
        logger.info("Student with ID {} deleted successfully", studentId);
        return student;
    }

    public void deleteAllStudents() {
        logger.info("Attempting to delete all students");
        validateAnyStudentExists();
        studentrepository.deleteAll();
        logger.info("All students deleted successfully");
    }

    public List<Student> getStudentsByGender(String gender) {
        logger.info("Fetching students by gender: {}", gender);
        List<Student> students = studentrepository.findByStudentGenderIgnoreCase(gender);
        validateStudentsNotEmpty(students);
        return students;
    }
    public List<Student> getStudentsByEmailDomain(String domain) {
        logger.info("Fetching students by email domain: {}", domain);
        List<Student> students = studentrepository.findAll();
        List<Student> filtered = new java.util.ArrayList<Student>();
        for (Student s : students) {
            if (s.getStudentEmail() != null && s.getStudentEmail().toLowerCase().endsWith("@" + domain.toLowerCase())) {
                filtered.add(s);
            }
        }
        validateStudentsNotEmpty(filtered);
        return filtered;
    }
    public List<Student> getTopNStudents(int n) {
        logger.info("Fetching top {} students sorted by name", n);
        List<Student> students = studentrepository.findAll();

        sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getStudentName().compareToIgnoreCase(s2.getStudentName());
            }
        });
        List<Student> topN = new java.util.ArrayList<Student>();
        for (int i = 0; i < n && i < students.size(); i++) {
            topN.add(students.get(i));
        }
        validateStudentsNotEmpty(topN);
        return topN;
    }
    public boolean isStudentExistsWithName(String name) {
        logger.info("Checking existence of student with name: {}", name);
        List<Student> students = studentrepository.findAll();
        for (Student student : students) {
            if (student.getStudentName() != null && student.getStudentName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    public void archiveAndDeleteAll() {
        logger.info("Archiving students before deletion");
        List<Student> students = getAllStudents(); // calls internal method
        for (Student student : students) {
            logger.info("Archived student: {}", student);
        }
        deleteAllStudents(); // calls existing method
    }
    public Student safeUpdateStudentEmail(Long studentId, String newEmail) {
        logger.info("Updating email for student ID {} to {}", studentId, newEmail);
        if (studentrepository.existsByStudentEmail(newEmail)) {
            logger.warn("Duplicate email detected while updating: {}", newEmail);
            throw new DuplicateFoundException("Email already exists: " + newEmail);
        }
        Student student = findStudentOrThrow(studentId);
        student.setStudentEmail(newEmail);
        return studentrepository.save(student);
    }

    private void validateDuplicateEmail(String email) {
        if (studentrepository.existsByStudentEmail(email)) {
            logger.warn("Duplicate student email found: {}", email);
            throw new DuplicateFoundException("Student with email [" + email + "] already exists");
        }
    }

    private Student findStudentOrThrow(Long studentId) {
        return studentrepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found with ID: {}", studentId);
                    return new StudentNotFoundException("Student not found with ID: " + studentId);
                });
    }
    private void validateStudentsNotEmpty(List<Student> students) {
        if (students == null || students.isEmpty()) {
            logger.warn("No students found in database");
            throw new NoDataAvailableException("No Records are available");
        }
    }
    private void validateAnyStudentExists() {
        if (studentrepository.count() == 0) {
            logger.warn("No students found to delete");
            throw new StudentNotFoundException("No students found to delete.");
        }
    }
}
