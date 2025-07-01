package com.springboot.model;
import org.springframework.hateoas.RepresentationModel;


public class StudentModel extends RepresentationModel<StudentModel> {

    private Long studentId;
    private String studentName;
    private String studentGender;
    private String studentEmail;

    public StudentModel(Student student) {
        this.studentId = student.getStudentId();
        this.studentName = student.getStudentName();
        this.studentGender = student.getStudentGender();
        this.studentEmail = student.getStudentEmail();
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
