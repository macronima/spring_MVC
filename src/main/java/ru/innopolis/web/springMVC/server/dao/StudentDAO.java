package ru.innopolis.web.springMVC.server.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.web.springMVC.common.dao.StudentDAOInterface;
import ru.innopolis.web.springMVC.common.dto.ListDTO;
import ru.innopolis.web.springMVC.common.dto.StudentDTO;
import ru.innopolis.web.springMVC.common.entitys.Student;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "studentDAO")
public class StudentDAO implements StudentDAOInterface {
    private static Logger logger = LoggerFactory.getLogger(StudentDAO.class);
    Integer perPage = 10;
    @Autowired
    DbPool dbPool;

    @Override
    public List<Student> getList(ListDTO listDTO) {
        List<Student> list = new ArrayList<>();
        try (Connection conn = dbPool.getConn();
             PreparedStatement st = conn.prepareStatement(buildSQLByListDTO(listDTO))) {
            int index = 0;
            if (listDTO.getNameFilter() != null) {
                st.setString(++index, listDTO.getNameFilter());
            }
            st.setLong(++index, perPage);
            st.setLong(++index, listDTO.getOffset());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(buildStudent(rs));
            }
        } catch (SQLException e) {
            logger.error("getList exception",e);
        }
        return list;
    }

    @Override
    public Student getById(Long id) {
        Student student = null;
        try (Connection conn = dbPool.getConn();
             PreparedStatement st = conn.prepareStatement("SELECT id, first_name, last_name, sex, birth FROM students WHERE id = ?")) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                student = buildStudent(rs);
            }
        } catch (SQLException e) {
            logger.error("getById exception",e);
        }
        return student;
    }

    @Override
    public Long add(StudentDTO studentDTO) {
        Long id = null;
        try (Connection conn = dbPool.getConn();
             PreparedStatement st = conn.prepareStatement("INSERT INTO students (first_name, last_name, sex, birth) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            fillStudentStatement(studentDTO, st);
            int affectedRows = st.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Creating student failed");
            }
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                   id =  generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("add exception",e);
        }
        return id;
    }

    private void fillStudentStatement(StudentDTO studentDTO, PreparedStatement st) throws SQLException {
        st.setString(1, studentDTO.getFirstName());
        st.setString(2, studentDTO.getLastName());
        st.setString(3, studentDTO.getSex());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            st.setDate(4, new Date(sdf.parse(studentDTO.getBirth()).getTime()));
        } catch (ParseException e) {
            logger.warn("Invalid date format on fillStudentStatement {}",studentDTO.getBirth());
            st.setDate(4, new Date(0));

        }
    }

    @Override
    public Long update(Long id, StudentDTO studentDTO) {
        Long res = null;
        try (Connection conn = dbPool.getConn();
             PreparedStatement st = conn.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, sex = ?, birth = ? WHERE id = ?")) {
            fillStudentStatement(studentDTO, st);
            st.setLong(5, id);
            st.executeUpdate();
            res = id;
        } catch (SQLException e) {
            logger.error("update exception",e);
        }
        return res;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        try (Connection conn = dbPool.getConn();
             PreparedStatement st = conn.prepareStatement("DELETE FROM students WHERE id = ?")) {
        st.setLong(1, id);
            res = st.execute();
        }catch (SQLException e){
            logger.error("deleteById exception",e);
        }
        return res;
    }

    private String buildSQLByListDTO(ListDTO listDTO) {
        StringBuilder s = new StringBuilder();
        s.append("SELECT id, first_name, last_name, sex, birth FROM students ");
        if (listDTO.getNameFilter() != null) {
            s.append("WHERE first_name= ?");
        }
        s.append(" ORDER BY ");
        switch (listDTO.getOrderField()) {
            case "first_name":
            case "last_name":
            case "sex":
            case "birth":
                s.append(listDTO.getOrderField());
                break;
            default:
                s.append("id");
                break;
        }
        s.append(" ");
        if ("desc".equals(listDTO.getOrderDirection())) {
            s.append("DESC");
        } else {
            s.append("ASC");
        }
        s.append(" LIMIT ? OFFSET ?");
        return s.toString();
    }

    private Student buildStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setSex(rs.getString("sex"));
        student.setBirth(new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("birth")));
        return student;
    }

}
