package com.programowanie.zespolowe.pz.api.devices;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.DeviceDAO;
import com.programowanie.zespolowe.pz.dao.RoleDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.entities.Role;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.DeviceCreateDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Basic;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static com.programowanie.zespolowe.pz.authapi.security.SecurityConstants.HEADER_STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceManagementTest {

    @Autowired
    DeviceManagement deviceManagement;

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
        Mockito.doReturn(testUser).when(commonUtil).getUserFromHeader(headers);
        DeviceCreateDTO device = new DeviceCreateDTO();
        device.setName("test");
        device.setMacAdress("12345678909123");
        ResponseEntity response = deviceManagement.register(device, headers);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Device created.",getResult(response));

    }

    @Test
    public void UnSuccessRegister() {
        Mockito.doReturn(new Device()).when(deviceDAO).findByNameAndUser(anyString(),any(User.class));
        Mockito.doReturn(new Device()).when(deviceDAO).findByMacAdressAndUser(any(),any());

        HttpHeaders headers = new HttpHeaders();
        Mockito.doReturn(testUser).when(commonUtil).getUserFromHeader(headers);
        DeviceCreateDTO device = new DeviceCreateDTO();
        device.setName("test");
        device.setMacAdress("12345678909123");
        ResponseEntity response = deviceManagement.register(device, headers);
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assert.assertEquals("Duplicate mac or name.",getResult(response));

    }

    @Test
    public void registerServerError() {
        MockitoAnnotations.initMocks(this);
        HttpHeaders headers = new HttpHeaders();
        Mockito.doReturn(testUser).when(commonUtil).getUserFromHeader(headers);

        DeviceCreateDTO deviceDTO = new DeviceCreateDTO();
        deviceDTO.setMacAdress("12345678909123");
        deviceDTO.setName("123");

        Device device = new Device();
        device.setMacAdress(deviceDTO.getMacAdress());
        device.setName(deviceDTO.getName());
        device.setUser(testUser);
        Mockito.when(deviceDAO.save(any(Device.class))).thenThrow(Mockito.mock(DataAccessException.class));

        ResponseEntity response = deviceManagement.register(deviceDTO, headers);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertEquals("Exception during device creating.",getResult(response));
    }

    @Test
    public void registerUserNotFound() {
        HttpHeaders headers = new HttpHeaders();
        Mockito.doReturn(null).when(commonUtil).getUserFromHeader(headers);
        ResponseEntity response = deviceManagement.register(null, headers);
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Assert.assertEquals("User not found.",getResult(response));
    }

    @Test
    public void getDevicesListInvalidUser() {
        HttpHeaders headers = new HttpHeaders();
        Mockito.doReturn(null).when(commonUtil).getUserFromHeader(headers);
        ResponseEntity response = deviceManagement.getDevicesList(headers);
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Assert.assertEquals("User not found.",getResult(response));
    }

    @Test
    public void getDevicesListServerError() {
        HttpHeaders headers = new HttpHeaders();
        User mockUser = Mockito.mock(User.class);
        Mockito.doThrow(new Exception()).when(mockUser).getDevices();
        Mockito.doReturn(mockUser).when(commonUtil).getUserFromHeader(headers);
        ResponseEntity response = deviceManagement.getDevicesList(headers);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assert.assertEquals("Server error.",getResult(response) );
    }

    @Test
    public void getDevicesListSuccess() {
        HttpHeaders headers = new HttpHeaders();
        User mockUser = Mockito.mock(User.class);
        List<Device> list = new ArrayList<>();
        Mockito.doReturn(list).when(mockUser).getDevices();
        Mockito.doReturn(mockUser).when(commonUtil).getUserFromHeader(headers);
        ResponseEntity response = deviceManagement.getDevicesList(headers);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteDeviceSuccess() {
        ResponseEntity response = deviceManagement.deleteDevice("0");
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertEquals("Device deleted.",getResult(response));
    }

    @Test
    public void deleteDeviceNotANumber() {
        Mockito.doThrow(new NumberFormatException()).when(deviceDAO).deleteById(anyInt());
        ResponseEntity response = deviceManagement.deleteDevice("0");
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        Assert.assertEquals("Not a number.",getResult(response));
    }

    @Test
    public void deleteDeviceServerError() {
        Mockito.doThrow(mock(DataAccessException.class)).when(deviceDAO).deleteById(anyInt());
        ResponseEntity response = deviceManagement.deleteDevice("0");
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        Assert.assertEquals("Server error.",getResult(response));
    }

    @Test
    public void getDeviceNotANumber() {
        Mockito.doThrow(Mockito.mock(NumberFormatException.class)).when(deviceDAO).findById(anyInt());
        ResponseEntity response = deviceManagement.getDevice("0");
        Assert.assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        Assert.assertEquals("Not a number.",getResult(response));
    }

    private String getResult(ResponseEntity response) {
        return gson.fromJson((String) response.getBody(), JsonElement.class).getAsJsonObject().get("result").getAsString();
    }
}