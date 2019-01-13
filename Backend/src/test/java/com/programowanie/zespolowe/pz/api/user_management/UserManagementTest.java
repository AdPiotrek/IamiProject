package com.programowanie.zespolowe.pz.api.user_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.programowanie.zespolowe.pz.entities.Role;
import com.programowanie.zespolowe.pz.model.UserRegisterDTO;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserManagementTest {

    @Autowired
    UserManagement userManagement;


    @Test
    public void register() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setEmail("test@test.com");
        userRegisterDTO.setFirstName("jan");
        userRegisterDTO.setLastName("kowalski");
        userRegisterDTO.setPassword("test123");
        ResponseEntity response = userManagement.register(userRegisterDTO);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());

        Gson gson = new Gson();
        String result = gson.fromJson((String)response.getBody(),JsonElement.class).getAsJsonObject().get("result").getAsString();
        Assert.assertEquals("User created",result);

        response = userManagement.register(userRegisterDTO);

        Assert.assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        result = gson.fromJson((String)response.getBody(),JsonElement.class).getAsJsonObject().get("result").getAsString();
        Assert.assertEquals("User already exists.",result);

        response = userManagement.register(new UserRegisterDTO());
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        result = gson.fromJson((String)response.getBody(),JsonElement.class).getAsJsonObject().get("result").getAsString();
        Assert.assertEquals("Server error.",result);

    }
}