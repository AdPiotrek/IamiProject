package com.programowanie.zespolowe.pz.api.deviceFamily;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.api.devices.DeviceManagement;
import com.programowanie.zespolowe.pz.dao.DeviceDAO;
import com.programowanie.zespolowe.pz.dao.RoleDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.Role;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.DeviceCreateDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceFamilyManagementTest {
    @Autowired
    DeviceFamilyManagement deviceFamilyManagement;

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleDAO roleDAO;

    @MockBean
    DeviceDAO deviceDAO;

    @SpyBean
    private CommonUtil commonUtil;

    private User testUser;
    private Gson gson = new Gson();


    @Before
    public void setUp() {
        Role role = new Role();
        role.setRole("testRole");
        roleDAO.save(role);
        User user = new User();
        user.setRole(role);
        user.setEmail("test");
        user.setName("test");
        user.setPassword("test");
        user.setSurname("test");
        testUser = userDAO.save(user);
    }

    @Test
    public void SuccessRegister() {
        Mockito.doReturn(null).when(deviceDAO).findByNameAndUser(anyString(),any(User.class));
        Mockito.doReturn(null).when(deviceDAO).findByMacAdressAndUser(any(),any());

        HttpHeaders headers = new HttpHeaders();
//        Mockito.doReturn(testUser).when(commonUtil).getTokenFromHeader(headers);
//
//        ResponseEntity response = deviceFamilyManagement.create("test",headers);
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assert.assertEquals("Device family created.",getResult(response));
    }

    private String getResult(ResponseEntity response) {
        return gson.fromJson((String) response.getBody(), JsonElement.class).getAsJsonObject().get("result").getAsString();
    }
}