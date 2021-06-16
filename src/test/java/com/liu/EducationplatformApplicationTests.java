package com.liu;

import com.liu.entity.Student;
import com.liu.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EducationplatformApplicationTests {
    @Autowired
    StudentService studentService;

    @Test
    void contextLoads() {
        Student student = studentService.getStudentById(17152010821L);
        System.out.println(student);
    }

}
