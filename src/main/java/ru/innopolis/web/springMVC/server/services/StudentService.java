package ru.innopolis.web.springMVC.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.web.springMVC.common.dao.StudentDAOInterface;
import ru.innopolis.web.springMVC.common.dto.ListDTO;
import ru.innopolis.web.springMVC.common.dto.StudentDTO;
import ru.innopolis.web.springMVC.common.entitys.Student;
import ru.innopolis.web.springMVC.common.services.StudentServiceInterface;

import java.util.List;


@Service("studentService")
public class StudentService implements StudentServiceInterface {
    @Autowired
    StudentDAOInterface studentDAO;

    @Override
    public List<Student> getList(ListDTO listDTO){
        return studentDAO.getList(listDTO);
    }

    @Override
    public Student getById(Long id) {
        Student student = studentDAO.getById(id);
        if(student == null){
            student = new Student();
            student.setFirstName("");
            student.setLastName("");
            student.setSex("");
            student.setBirth("");
            student.setId(0);
        }
        return student;
    }

    @Override
    public Long save(Long id, StudentDTO studentDTO) {
        Long res = null;
        if(id > 0l){
            res = studentDAO.update(id, studentDTO);
        }else{
            res = studentDAO.add(studentDTO);
        }
        return res;
    }

    @Override
    public boolean delete(Long id) {
        return studentDAO.deleteById(id);
    }
}
