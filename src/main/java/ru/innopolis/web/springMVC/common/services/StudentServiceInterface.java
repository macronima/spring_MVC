package ru.innopolis.web.springMVC.common.services;

import ru.innopolis.web.springMVC.common.dto.ListDTO;
import ru.innopolis.web.springMVC.common.dto.StudentDTO;
import ru.innopolis.web.springMVC.common.entitys.Student;

import java.util.List;

public interface StudentServiceInterface {
    /**
     *  Return list by listDTO
     * @param listDTO
     * @return list of Student
     */
    List<Student> getList(ListDTO listDTO);

    /**
     * Return student by id
     * @param id
     * @return Student entity
     */
    Student getById(Long id);

    /**
     * Add or update student by id, id=0 - cause adding new
     * @param id
     * @param studentDTO
     * @return id of saving row
     */
    Long save(Long id, StudentDTO studentDTO);

    /**
     * Delete student by id
     * @param id
     * @return isSuccess
     */
    boolean delete(Long id);
}
