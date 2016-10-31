package ru.innopolis.web.springMVC.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.k_sharypov.web.spring_mvc_practice.common.dto.ListDTO;
import ru.innopolis.k_sharypov.web.spring_mvc_practice.common.dto.StudentDTO;
import ru.innopolis.k_sharypov.web.spring_mvc_practice.common.services.StudentServiceInterface;
import ru.innopolis.k_sharypov.web.spring_mvc_practice.server.dao.StudentDAO;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class StudentController {
    private static Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Qualifier("studentService")
    @Autowired
    private StudentServiceInterface studentServiceInterface;


    @RequestMapping("")
    public String index(){
        return "redirect:/list";
    }

    @RequestMapping("list")
    public ModelAndView list(HttpServletRequest req){
        ModelAndView  mv = new ModelAndView();
        ListDTO listDTO = getRequestListTDO(req);
        mv.setViewName("index");
        mv.addObject("listDTO", listDTO);
        mv.addObject("list", studentServiceInterface.getList(listDTO));
        return mv;
    }

    @RequestMapping(value = "student/{id}", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest req, @PathVariable Long id){
        logger.info("Show {}",id);
        ModelAndView mv = new ModelAndView("form");
        mv.addObject("student", studentServiceInterface.getById(id));
        return mv;
    }

    @RequestMapping(value = "student/{id}", method = RequestMethod.POST)
    public String update(HttpServletRequest req, @PathVariable Long id){
        logger.info("Update {}",id);
        StudentDTO studentDTO = getRequestStudentDTO(req);
        Long studentId = studentServiceInterface.save(id, studentDTO);
        return "redirect:/student/"+ studentId;
    }

    @RequestMapping("student/{id}/del")
    public String delete(@PathVariable Long id){
        logger.info("Delete {}",id);
        studentServiceInterface.delete(id);
        return "redirect:/list";
    }

    private StudentDTO getRequestStudentDTO(HttpServletRequest req) {
        StudentDTO s = new StudentDTO();
        /**
         *  For propertly process non asci symbols in URI, add  URIEncoding="UTF-8" to <connector> in TOMCAT_DIR/conf/server.xml
         */
        s.setFirstName(req.getParameter("firstName"));
        s.setLastName(req.getParameter("lastName"));
        s.setSex(req.getParameter("sex"));
        s.setBirth(req.getParameter("birth"));
        return s;
    }

    private ListDTO getRequestListTDO(HttpServletRequest req){
        ListDTO l = new ListDTO();
        l.setNameFilter(req.getParameter("nameFilter"));
        if("".equals(l.getNameFilter())){
            l.setNameFilter(null);
        }
        l.setOrderField(req.getParameter("orderField"));
        if(l.getOrderField()==null || "".equals(l.getOrderField())){
            l.setOrderField("id");
        }
        l.setOrderDirection(req.getParameter("orderDirection"));
        if(l.getOrderDirection()==null || "".equals(l.getOrderDirection())){
            l.setOrderDirection("asc");
        }
        try {
            l.setOffset(Long.parseLong(req.getParameter("offset")));
        }catch (Exception e){
            l.setOffset(0l);
        }
        return l;
    }

}
