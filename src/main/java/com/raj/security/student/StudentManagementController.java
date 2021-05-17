package com.raj.security.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private  final List<Student> students = Arrays.asList(
            new Student (1, "Bruno Fernandes"),
            new Student (2, "Marcus Rashford"),
            new Student (3, "Edinson Cavani")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        return students;
    }
    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("registerNewStudent");
        System.out.println(student);
    }
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println(studentId);
    }
    @PutMapping(path="/{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s", studentId, student));
    }
}
