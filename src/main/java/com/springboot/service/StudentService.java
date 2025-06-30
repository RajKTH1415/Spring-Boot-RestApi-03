package com.springboot.service;

import com.springboot.exception.DuplicateFoundException;
import com.springboot.exception.NoDataAvailableException;
import com.springboot.exception.StudentNotFoundException;
import com.springboot.model.Student;
import com.springboot.repository.Studentrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private Studentrepository studentrepository;

	public StudentService(Studentrepository studentrepository) {
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
