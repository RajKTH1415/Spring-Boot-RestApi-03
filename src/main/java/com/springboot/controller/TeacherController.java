package com.springboot.controller;

import com.springboot.model.Teacher;
import com.springboot.respone.ApiResponse;
import com.springboot.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
@Tag(name = "Teacher controller", description = "APIs for Teacher management system")
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/save")
    @Operation(summary = "save a new Teacher", description = "Create and stores new T in the database")
    public ResponseEntity<ApiResponse> saveTeacher(@Valid @RequestBody Teacher teacher) {
        logger.info("Received request to save new teacher");
        Teacher teacher1 = teacherService.createTeacher(teacher);
        ApiResponse response = new ApiResponse(true, "Teacher added successfully", teacher1);
        logger.debug("Teacher created with ID: {}", teacher1.getTeacher_Id());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
