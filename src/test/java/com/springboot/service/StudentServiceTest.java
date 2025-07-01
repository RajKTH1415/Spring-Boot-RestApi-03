package com.springboot.service;

import com.springboot.exception.DuplicateFoundException;
import com.springboot.exception.NoDataAvailableException;
import com.springboot.exception.StudentNotFoundException;
import com.springboot.model.Student;
import com.springboot.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentrepository;

    @InjectMocks
    private StudentService studentService;

    private Student sampleStudent;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Call before all Test case :->");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Before each test case->");

        sampleStudent = new Student();
        sampleStudent.setStudentId(1L);
        sampleStudent.setStudentName("Rajkumar Prasad");
        sampleStudent.setStudentGender("Male");
        sampleStudent.setStudentEmail("raj@gmail.com");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    // Original Tests
    @Test
    void createStudentSuccessfully() {
        when(studentrepository.existsByStudentEmail(sampleStudent.getStudentEmail())).thenReturn(false);
        when(studentrepository.save(sampleStudent)).thenReturn(sampleStudent);

        Student saved = studentService.createStudent(sampleStudent);

        assertNotNull(saved);
        assertEquals(sampleStudent.getStudentEmail(), saved.getStudentEmail());
        verify(studentrepository, times(1)).save(sampleStudent);
    }

    @Test
    void createStudentThrowsDuplicateException() {
        when(studentrepository.existsByStudentEmail(sampleStudent.getStudentEmail())).thenReturn(true);

        assertThrows(DuplicateFoundException.class, () -> studentService.createStudent(sampleStudent));
        verify(studentrepository, never()).save(any());
    }

    @Test
    void getStudentByIdSuccessfully() {
        when(studentrepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Student student = studentService.getStudentById(1L);

        assertEquals("Rajkumar Prasad", student.getStudentName());
        verify(studentrepository, times(1)).findById(1L);
    }

    @Test
    void getStudentByIdThrowsException() {
        when(studentrepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void getAllStudentsSuccessfully() {
        when(studentrepository.findAll()).thenReturn(List.of(sampleStudent));

        List<Student> students = studentService.getAllStudents();

        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
        verify(studentrepository).findAll();
    }

    @Test
    void getAllStudentsThrowsNoDataException() {
        when(studentrepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataAvailableException.class, () -> studentService.getAllStudents());
    }

    @Test
    void updateStudentSuccessfully() {
        Student updatedStudent = new Student();
        updatedStudent.setStudentName("Updated");
        updatedStudent.setStudentGender("Other");
        updatedStudent.setStudentEmail("updated@gmail.com");

        when(studentrepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentrepository.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.updateStudent(1L, updatedStudent);

        assertEquals("Updated", result.getStudentName());
        assertEquals("Other", result.getStudentGender());
    }

    @Test
    void updateStudentThrowsNotFound() {
        when(studentrepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(1L, sampleStudent));
    }

    @Test
    void deleteStudentByIdSuccessfully() {
        when(studentrepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        doNothing().when(studentrepository).deleteById(1L);

        Student deleted = studentService.deleteStudentById(1L);

        assertEquals(sampleStudent.getStudentId(), deleted.getStudentId());
        verify(studentrepository).deleteById(1L);
    }

    @Test
    void deleteStudentByIdThrowsNotFound() {
        when(studentrepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudentById(1L));
    }

    @Test
    void deleteAllStudentsSuccessfully() {
        when(studentrepository.count()).thenReturn(5L);
        doNothing().when(studentrepository).deleteAll();

        studentService.deleteAllStudents();

        verify(studentrepository).deleteAll();
    }

    @Test
    void deleteAllStudentsThrowsNotFound() {
        when(studentrepository.count()).thenReturn(0L);

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteAllStudents());
    }

    // ✅ New Tests

    @Test
    void getStudentsByGenderSuccessfully() {
        when(studentrepository.findByStudentGenderIgnoreCase("Male")).thenReturn(List.of(sampleStudent));

        List<Student> students = studentService.getStudentsByGender("Male");

        assertFalse(students.isEmpty());
        assertEquals("Male", students.get(0).getStudentGender());
    }

    @Test
    void getStudentsByGenderThrowsException() {
        when(studentrepository.findByStudentGenderIgnoreCase("Male")).thenReturn(Collections.emptyList());

        assertThrows(NoDataAvailableException.class, () -> studentService.getStudentsByGender("Male"));
    }

    @Test
    void getStudentsByEmailDomainSuccessfully() {
        when(studentrepository.findAll()).thenReturn(List.of(sampleStudent));

        List<Student> students = studentService.getStudentsByEmailDomain("gmail.com");

        assertEquals(1, students.size());
        assertTrue(students.get(0).getStudentEmail().endsWith("@gmail.com"));
    }

    @Test
    void getStudentsByEmailDomainThrowsNoData() {
        when(studentrepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataAvailableException.class, () -> studentService.getStudentsByEmailDomain("gmail.com"));
    }

    @Test
    void getTopNStudentsSuccessfully() {
        Student s2 = new Student();
        s2.setStudentId(2L);
        s2.setStudentName("Amit");
        s2.setStudentGender("Male");
        s2.setStudentEmail("amit@gmail.com");

        when(studentrepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(sampleStudent, s2)));

        List<Student> topStudents = studentService.getTopNStudents(1);

        assertEquals(1, topStudents.size());
    }

    @Test
    void getTopNStudentsThrowsIfEmpty() {
        when(studentrepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoDataAvailableException.class, () -> studentService.getTopNStudents(2));
    }

    @Test
    void isStudentExistsWithNameReturnsTrue() {
        when(studentrepository.findAll()).thenReturn(List.of(sampleStudent));

        assertTrue(studentService.isStudentExistsWithName("Rajkumar Prasad"));
    }

    @Test
    void isStudentExistsWithNameReturnsFalse() {
        when(studentrepository.findAll()).thenReturn(List.of());

        assertFalse(studentService.isStudentExistsWithName("Unknown"));
    }

    @Test
    void archiveAndDeleteAllSuccessfully() {
        when(studentrepository.findAll()).thenReturn(List.of(sampleStudent));
        when(studentrepository.count()).thenReturn(1L);
        doNothing().when(studentrepository).deleteAll();

        studentService.archiveAndDeleteAll();

        verify(studentrepository).deleteAll();
    }

    @Test
    void safeUpdateStudentEmailSuccessfully() {
        when(studentrepository.existsByStudentEmail("newraj@gmail.com")).thenReturn(false);
        when(studentrepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentrepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student updated = studentService.safeUpdateStudentEmail(1L, "newraj@gmail.com");

        assertEquals("newraj@gmail.com", updated.getStudentEmail());
    }

    @Test
    void safeUpdateStudentEmailThrowsDuplicate() {
        when(studentrepository.existsByStudentEmail("newraj@gmail.com")).thenReturn(true);

        assertThrows(DuplicateFoundException.class,
                () -> studentService.safeUpdateStudentEmail(1L, "newraj@gmail.com"));
    }

    @Test
    void dummyTest() {
        System.out.println("Dummy test");
        assertTrue(true);
    }
}
