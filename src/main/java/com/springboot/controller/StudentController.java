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

	private ResponseEntity<ApiResponse> buildResponse(boolean success, String message, Object data, HttpStatus status) {
		return new ResponseEntity<>(new ApiResponse(success, message, data), status);
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

	@GetMapping("/gender/{gender}")
	public ResponseEntity<ApiResponse> getStudentsByGender(@PathVariable String gender) {
		logger.info("Fetching students with gender: {}", gender);
		List<Student> students = studentService.getStudentsByGender(gender);
		ApiResponse response = new ApiResponse(true, "Students fetched by gender", students);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/email-domain/{domain}")
	public ResponseEntity<ApiResponse> getStudentsByEmailDomain(@PathVariable String domain) {
		logger.info("Fetching students with email domain: {}", domain);
		List<Student> students = studentService.getStudentsByEmailDomain(domain);
		ApiResponse response = new ApiResponse(true, "Students fetched by email domain", students);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/top/{n}")
	public ResponseEntity<ApiResponse> getTopNStudents(@PathVariable int n) {
		logger.info("Fetching top {} students", n);
		List<Student> topStudents = studentService.getTopNStudents(n);
		return buildResponse(true, "Top " + n + " students fetched successfully", topStudents, HttpStatus.OK);
	}

	@GetMapping("/exists/{name}")
	public ResponseEntity<ApiResponse> isStudentExistsByName(@PathVariable String name) {
		logger.info("Checking existence of student by name: {}", name);
		boolean exists = studentService.isStudentExistsWithName(name);
		String message = exists ? "Student exists" : "Student not found";
		return buildResponse(true, message, exists, HttpStatus.OK);
	}

	@DeleteMapping("/archive-delete")
	public ResponseEntity<ApiResponse> archiveAndDeleteAllStudents() {
		logger.warn("Archiving and deleting all students");
		studentService.archiveAndDeleteAll();
		return buildResponse(true, "All students archived and deleted", null, HttpStatus.OK);
	}

	@PatchMapping("/{studentId}/update-email")
	public ResponseEntity<ApiResponse> safeUpdateStudentEmail(@PathVariable Long studentId,
															  @RequestParam String newEmail) {
		logger.info("Safely updating email for student ID: {}", studentId);
		Student updatedStudent = studentService.safeUpdateStudentEmail(studentId, newEmail);
		return buildResponse(true, "Student email updated successfully", updatedStudent, HttpStatus.OK);
	}
}