package com.programowanie.zespolowe.pz.api.history;

import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.BlobDAO;
import com.programowanie.zespolowe.pz.dao.DeviceDAO;
import com.programowanie.zespolowe.pz.dao.HistoryDAO;
import com.programowanie.zespolowe.pz.entities.History;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.HistoryEntryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HistoryManagement implements HistoryAPI{

    Logger log = LoggerFactory.getLogger(HistoryManagement.class);

    @Autowired
    HistoryDAO historyDAO;
    @Autowired
    DeviceDAO deviceDAO;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    BlobDAO blobDAO;

    @Override
    public ResponseEntity register(@RequestBody HistoryEntryDTO historyEntryDTO, @RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        try {
            History history = new History();
            history.setBlob(blobDAO.findById(Integer.parseInt(historyEntryDTO.getBlobId())).get());
            history.setDeviceMac(deviceDAO.findById(Integer.parseInt(historyEntryDTO.getDeviceId())).get().getMacAdress());
            history.setDeviceName(deviceDAO.findById(Integer.parseInt(historyEntryDTO.getDeviceId())).get().getName());
            history.setUser(user);
            historyDAO.save(history);
        } catch (Exception e){
            log.warn("Exception on history save", e);
            return commonUtil.getResponseEntity("Exception on saving history event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("History event created.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity getHistoryEventList(@RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        List<History> historyList;
        try {
            historyList = historyDAO.getByUser(user);
        } catch (Exception e){
            log.warn("Exception on history get", e);
            return commonUtil.getResponseEntity("Exception on saving history event.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body(historyList);
    }

}
