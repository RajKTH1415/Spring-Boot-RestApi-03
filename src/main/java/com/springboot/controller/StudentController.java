package com.springboot.controller;

import com.springboot.model.Student;
import com.springboot.respone.ApiResponse;
import com.springboot.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
	public ResponseEntity<ApiResponse> saveStudent(@Valid @RequestBody Student student) {
		logger.info("Received request to save new student");
		Student student_1 = studentService.createStudent(student);
		ApiResponse response = new ApiResponse(true, "Student added successfully", student_1);
		logger.debug("Student created with ID: {}", student_1.getStudentId());
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long studentId) {
		logger.info("Fetching student with ID: {}", studentId);
		Student student_2 = studentService.getStudentById(studentId);
		ApiResponse response = new ApiResponse(true, "Student is present with given Id :" + studentId, student_2);
		logger.debug("Successfully fetched student with ID: {}", studentId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAllEmployee() {
		logger.info("Fetching all students");
		List<Student> student_3 = studentService.getAllStudents();
		ApiResponse response = new ApiResponse(true, "Getting all Objects are successfully..", student_3);
		logger.debug("Fetched {} students", student_3.size());
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long studentId,
													 @Valid @RequestBody Student student) {
		logger.info("Updating student with ID: {}", studentId);
		Student student_4 = studentService.updateStudent(studentId, student);
		ApiResponse response = new ApiResponse(true, "Student updated successfully", student_4);
		logger.debug("Student with ID {} updated successfully", studentId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{studentId}")
	public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long studentId) {
		logger.info("Deleting student with ID: {}", studentId);
		Student student_5 = studentService.deleteStudentById(studentId);
		ApiResponse response = new ApiResponse(true, "Student deleted successfully..!!", student_5);
		logger.info("Student with ID {} deleted", studentId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<ApiResponse> deleteAllStudents() {
		logger.warn("Received request to delete all students");
		studentService.deleteAllStudents();
		ApiResponse response = new ApiResponse(true, "All students deleted successfully!", null);
		logger.info("All students deleted");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}