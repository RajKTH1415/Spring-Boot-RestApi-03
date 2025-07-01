package com.springboot.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.Student;
import com.springboot.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setStudentName("Rajkumar Prasad");
        student.setStudentGender("Male");
        student.setStudentEmail("raj@gmail.com");
    }

    @Test
    void testSaveStudent() throws Exception {
        Mockito.when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/student/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Student added successfully"))
                .andExpect(jsonPath("$.data.studentId").value(1));
    }

    @Test
    void testGetStudentById() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/api/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Student is present with given Id :1"))
                .andExpect(jsonPath("$.data.studentId").value(1));
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = Collections.singletonList(student);
        Mockito.when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Getting all Objects are successfully.."))
                .andExpect(jsonPath("$.data[0].studentId").value(1));
    }

    @Test
    void testUpdateStudent() throws Exception {
        student.setStudentName("Updated Name");
        Mockito.when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/api/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Student updated successfully"))
                .andExpect(jsonPath("$.data.studentName").value("Updated Name"));
    }

    @Test
    void testDeleteStudentById() throws Exception {
        Mockito.when(studentService.deleteStudentById(1L)).thenReturn(student);

        mockMvc.perform(delete("/api/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Student deleted successfully..!!"))
                .andExpect(jsonPath("$.data.studentId").value(1));
    }

    @Test
    void testDeleteAllStudents() throws Exception {
        mockMvc.perform(delete("/api/student/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("All students deleted successfully!"));
    }
}
