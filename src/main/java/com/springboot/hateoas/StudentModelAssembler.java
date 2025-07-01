package com.springboot.hateoas;

import com.springboot.controller.StudentController;
import com.springboot.model.Student;
import com.springboot.model.StudentModel;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<Student, StudentModel> {
    @Override
    public StudentModel toModel(Student student) {
        StudentModel model = new StudentModel(student);
        model.add(linkTo(methodOn(StudentController.class).getStudentById(student.getStudentId())).withSelfRel());
        model.add(linkTo(methodOn(StudentController.class).getAllEmployee()).withRel("all-students"));
        model.add(linkTo(methodOn(StudentController.class).deleteStudent(student.getStudentId())).withRel("delete"));
        model.add(linkTo(methodOn(StudentController.class).updateStudent(student.getStudentId(), student)).withRel("update"));
        return model;

    }
}
