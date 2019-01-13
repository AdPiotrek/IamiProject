package com.programowanie.zespolowe.pz.api;

import com.programowanie.zespolowe.pz.dao.DeviceFamilyDAO;
import com.programowanie.zespolowe.pz.dao.RoleDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld  {

    Logger log = LoggerFactory.getLogger(HelloWorld.class);

    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    DeviceFamilyDAO groupDAO;

    @RequestMapping("/pz")
    public String hello(){
        User user = new User();
        user.setSurname("test");
        user.setName("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setRole(roleDAO.findByRole("user"));
        userDAO.save(user);
        return "Hello World";
    }
}
