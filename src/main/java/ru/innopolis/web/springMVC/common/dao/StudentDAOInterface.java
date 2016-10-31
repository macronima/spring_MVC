package ru.innopolis.web.springMVC.common.dao;


import ru.innopolis.web.springMVC.common.dto.ListDTO;
import ru.innopolis.web.springMVC.common.dto.StudentDTO;
import ru.innopolis.web.springMVC.common.entitys.Student;

import java.util.List;

public interface StudentDAOInterface {
    /**
     *  Return list by ListDTO statements
     * @param listDTO
     * @return
     */
    List<Student> getList(ListDTO listDTO);

    /**
     * Return student by id
     * @param id
     * @return Student
     */
    Student getById(Long id);

    /**
     * Insert student to DB
     * @param studentDTO
     * @return Long id
     */
    Long add(StudentDTO studentDTO);

    /**
     * Update student
     * @param studentDTO
     * @return Long id
     */
    Long update(Long id, StudentDTO studentDTO);


    /**
     * Delete student by id
     * @param id
     * @return boolean isSuccess
     */
    boolean deleteById(Long id);

}
