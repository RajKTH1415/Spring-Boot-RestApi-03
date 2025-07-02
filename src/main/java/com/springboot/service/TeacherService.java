package com.springboot.service;

import com.springboot.exception.DuplicateFoundException;
import com.springboot.model.Teacher;
import com.springboot.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher createTeacher(Teacher teacher) {
        logger.info("Attempting to create Teacher with email: {}", teacher.getTeacherEmail());
        validateDuplicateEmail(teacher.getTeacherEmail());
        Teacher saveTeacher = teacherRepository.save(teacher);
        logger.info("Teacher created successfully with ID: {}", saveTeacher.getTeacher_Id());
        return saveTeacher;
    }

    private void validateDuplicateEmail(String email) {
        if (teacherRepository.existsByTeacherEmail(email)) {
            logger.warn("Duplicate Teacher email found: {}", email);
            throw new DuplicateFoundException("Teacher with email [" + email + "] already exists");
        }
    }
}
