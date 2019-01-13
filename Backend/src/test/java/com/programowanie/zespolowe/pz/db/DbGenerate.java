package com.programowanie.zespolowe.pz.db;

import com.programowanie.zespolowe.pz.PzApplication;
import com.programowanie.zespolowe.pz.dao.BlobDAO;
import com.programowanie.zespolowe.pz.dao.DeviceFamilyDAO;
import com.programowanie.zespolowe.pz.dao.RoleDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.Role;
import com.programowanie.zespolowe.pz.entities.User;
import org.h2.jdbc.JdbcSQLException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class DbGenerate {


    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BlobDAO blobDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    DeviceFamilyDAO deviceFamilyDAO;

    @Autowired
    RoleDAO roleDAO;

    @Test
    public void createUserAndRole(){
        Role role = new Role();
        role.setRole("testRole");
        Role generatedRole = entityManager.persist(role);

        Assert.assertEquals(generatedRole,roleDAO.findByRole("testRole"));

        User user = new User();
        user.setName("createUserAndRole");
        user.setEmail("createUserAndRole@email.com");
        user.setRole(generatedRole);
        User genUser = entityManager.persist(user);

        Assert.assertEquals(genUser,userDAO.findByEmail("createUserAndRole@email.com"));
        Assert.assertEquals("testRole",genUser.getRole().getRole());

    }

    @Test(expected = PersistenceException.class)
    public void userConstraintException(){
        User user = new User();
        user.setName("createUserAndRole");
        user.setEmail("createUserAndRole@email.com");
       entityManager.persist(user);
    }

    @Test(expected = PersistenceException.class)
    public void roleConstraintException(){
        Role role = new Role();
        entityManager.persist(role);
    }

}
