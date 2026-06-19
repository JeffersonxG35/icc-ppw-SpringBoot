package ec.edu.ups.icc.fundamentos01.students.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.students.models.Student;

@RestController
@RequestMapping("/students")

public class StudentController {
private List<Student> students = new ArrayList<>();

 public StudentController(){
    students.add(new Student(1, "Juan", 30));
    students.add(new Student(2, "Jefferson", 22));
 }   

 @GetMapping()
 public List<Student> getAllStudents() {
    return students;

 }

 @GetMapping("/count")
 public String getCount (){
    return "Total students: " + students.size();
 }
}
