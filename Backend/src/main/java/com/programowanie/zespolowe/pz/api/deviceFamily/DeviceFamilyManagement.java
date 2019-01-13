package com.programowanie.zespolowe.pz.api.deviceFamily;

import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.Utils.DataBaseOperations;
import com.programowanie.zespolowe.pz.dao.DeviceDAO;
import com.programowanie.zespolowe.pz.dao.DeviceFamilyDAO;
import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.entities.Devicefamily;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.CreateFamilyDeviceDTO;
import com.programowanie.zespolowe.pz.model.DeviceFamilyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.xml.ws.RequestWrapper;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class DeviceFamilyManagement implements DeviceFamilyAPI {

    Logger log = LoggerFactory.getLogger(DeviceFamilyManagement.class);

    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DeviceDAO deviceDAO;
    @Autowired
    DeviceFamilyDAO deviceFamilyDAO;

    @Override
    public ResponseEntity create(@RequestBody CreateFamilyDeviceDTO deviceFamilyDTO, @RequestHeader HttpHeaders headers) {
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        if (deviceFamilyDAO.existsByFamilyNameAndUser((String)deviceFamilyDTO.getFamilyName(),user)) {
            return commonUtil.getResponseEntity("Family with this name exist", HttpStatus.CONFLICT);
        }
        return persistDevice((String)deviceFamilyDTO.getFamilyName(),user);
    }

    private ResponseEntity persistDevice(String familyName,User user) {
        try {
            Devicefamily deviceFamily = new Devicefamily();
            deviceFamily.setUser(user);
            deviceFamily.setFamilyName(familyName);
            deviceFamilyDAO.save(deviceFamily);
        } catch (DataAccessException e) {
            log.warn("Device creating exception", e);
            return commonUtil.getResponseEntity("Exception during deviceFamily creating."
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Device family created.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getDevicesFamilyList(@RequestHeader HttpHeaders headers) {
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        List<Devicefamily> deviceFamilies;
        try {
            deviceFamilies = deviceFamilyDAO.findByUser(user);
        } catch (Exception e) {
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(deviceFamilies);
    }

    @Override
    public ResponseEntity deleteDeviceFamily(String id) {
        try {
            deviceFamilyDAO.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException n) {
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Device family deleted.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getDeviceFamily(@PathVariable(value = "familyId") String deviceFamilyId, @RequestHeader HttpHeaders headers) {
        return DataBaseOperations.getById(deviceFamilyId, deviceFamilyDAO, commonUtil);
    }

    @Override
    public ResponseEntity editFamily(@RequestBody Devicefamily devicefamily) {
        try {
            deviceFamilyDAO.save(devicefamily);
            return ResponseEntity.status(HttpStatus.OK).body(devicefamily);
        } catch (NumberFormatException n) {
            return commonUtil.getResponseEntity("Id is not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return commonUtil.getResponseEntity("Device family not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.warn("Unknown exception", e);
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity addDeviceToFamily(@RequestBody DeviceFamilyDTO deviceFamilyDTO) {
        if (!deviceFamilyDAO.existsById(deviceFamilyDTO.getFamilyID())) {
            return commonUtil.getResponseEntity("Device family not found.", HttpStatus.NOT_FOUND);
        }
        try {
            Device device = deviceDAO.findById(deviceFamilyDTO.getDeviceID()).get();
            if (deviceFamilyDAO.existsByDevicesEqualsAndIdDeviceFamilies(device, deviceFamilyDTO.getFamilyID()))
                return commonUtil.getResponseEntity("This device exist in family", HttpStatus.CONFLICT);
            else {
                Devicefamily devicefamily = deviceFamilyDAO.findById(deviceFamilyDTO.getFamilyID()).get();
                devicefamily.getDevices().add(device);
                deviceFamilyDAO.save(devicefamily);
                return commonUtil.getResponseEntity("Device added to family", HttpStatus.OK);
            }
        } catch (NumberFormatException n) {
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return commonUtil.getResponseEntity("Device not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity removeDeviceToFamily(@PathVariable(value = "familyId") String familyId, @PathVariable(value = "deviceId") String deviceId, @RequestHeader HttpHeaders headers) {

        DeviceFamilyDTO deviceFamilyDTO = new DeviceFamilyDTO();
        deviceFamilyDTO.setDeviceID(Integer.parseInt(deviceId));
        deviceFamilyDTO.setFamilyID(Integer.parseInt(familyId));

        if (!deviceFamilyDAO.existsById(deviceFamilyDTO.getFamilyID())) {
            return commonUtil.getResponseEntity("Device family not found.", HttpStatus.NOT_FOUND);
        }
        try {
            Device device = deviceDAO.findById(deviceFamilyDTO.getDeviceID()).get();
            if (!deviceFamilyDAO.existsByDevicesEqualsAndIdDeviceFamilies(device, deviceFamilyDTO.getFamilyID()))
                return commonUtil.getResponseEntity("This device doesn't exists in family", HttpStatus.CONFLICT);
            else {
                Devicefamily devicefamily = deviceFamilyDAO.findById(deviceFamilyDTO.getFamilyID()).get();
                devicefamily.getDevices().remove(device);
                deviceFamilyDAO.save(devicefamily);
                return commonUtil.getResponseEntity("Device deleted from family", HttpStatus.OK);
            }
        } catch (NumberFormatException n) {
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return commonUtil.getResponseEntity("Device not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}