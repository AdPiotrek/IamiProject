package com.programowanie.zespolowe.pz.api.devices;

import com.google.gson.Gson;
import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.DeviceDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.DeviceCreateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Api dostępowe do zarządzania urządzeniami.
 */
@RestController
public class DeviceManagement implements DeviceAPI{

    Logger log = LoggerFactory.getLogger(DeviceManagement.class);

    @Autowired
    DeviceDAO deviceDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CommonUtil commonUtil;

    /**
     * @inheritDoc
     *
     * @param device  - urzędzenie które ma być dodane.
     * @param headers - nagłówek
     * @return Zwraca wiadomośc z odpowiednim statusem
     */
    @Override
    public ResponseEntity register(@Valid @RequestBody DeviceCreateDTO device, @RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);

        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }

        Device existingName = deviceDAO.findByNameAndUser(device.getName(), user);
        Device existingMac = deviceDAO.findByMacAdressAndUser(device.getName(), user);
        if(existingMac == null && existingName == null){
            return persistDevice(device, user);
        }
        return commonUtil.getResponseEntity("Duplicate mac or name.", HttpStatus.CONFLICT);
    }

    private ResponseEntity persistDevice(DeviceCreateDTO deviceDTO, User user){
        try {
            Device device = new Device();
            device.setName(deviceDTO.getName());
            device.setMacAdress(deviceDTO.getMacAdress());
            device.setUser(user);
            deviceDAO.save(device);
        } catch (DataAccessException e) {
            log.warn("Device creating exception", e);
            return commonUtil.getResponseEntity("Exception during device creating."
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Device created.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getDevicesList(@RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        List<Device> devices;
        try {
            devices = user.getDevices();
        } catch (Exception e) {
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

    @Override
    public ResponseEntity deleteDevice(@PathVariable(value = "deviceId") String deviceId){
        try{
            deviceDAO.deleteById(Integer.parseInt(deviceId));
        } catch (NumberFormatException n){
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Device deleted.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getDevice(@PathVariable(value = "deviceId") String deviceId){
        try{
            Device device = deviceDAO.findById(Integer.parseInt(deviceId)).get();
            return ResponseEntity.status(HttpStatus.OK).body(device);
        } catch (NumberFormatException n){
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e){
            return commonUtil.getResponseEntity("Device not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.warn("Unknown exception", e);
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity editDevice(@RequestBody Device device){
        try{
            Device dbDeviceRef = deviceDAO.findById(device.getDeviceid()).get();
            dbDeviceRef.setName(device.getName());
            dbDeviceRef.setMacAdress(device.getMacAdress());
            deviceDAO.save(dbDeviceRef);
            return ResponseEntity.status(HttpStatus.OK).body(device);
        } catch (NumberFormatException n){
            return commonUtil.getResponseEntity("Id is not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e){
            return commonUtil.getResponseEntity("Device not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            log.warn("Unknown exception", e);
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
