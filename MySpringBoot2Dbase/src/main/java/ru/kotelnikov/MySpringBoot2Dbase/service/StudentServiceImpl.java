package ru.kotelnikov.MySpringBoot2Dbase.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kotelnikov.MySpringBoot2Dbase.dao.StudentDAO;
import ru.kotelnikov.MySpringBoot2Dbase.entity.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        return studentDAO.saveStudent(student);
    }

    @Override
    @Transactional
    public Student getStudent(int id) {
        Student student = studentDAO.getStudent(id);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        return student;
    }

    @Override
    @Transactional
    public void deleteStudent(int id) {
        Student student = studentDAO.getStudent(id);
        if (student == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentDAO.deleteStudent(id);
    }
}

/*package ru.kotelnikov.MySpringBoot2Dbase.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kotelnikov.MySpringBoot2Dbase.dao.StudentDAO;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents(){
        return studentDAO.getAllStudents();
    }
    @Override
    @Transactional
    public Student saveStudent(Student student){
        return studentDAO.saveStudent(student);
    }
    @Override
    @Transactional
    public Student getStudent(int id) {
        return studentDAO.getStudent(id);
    }
    @Override
    @Transactional
    public void deleteStudent(int id) {
         studentDAO.deleteStudent(id);
    }
}*/
