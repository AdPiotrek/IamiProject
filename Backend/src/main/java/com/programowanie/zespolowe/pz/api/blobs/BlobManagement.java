package com.programowanie.zespolowe.pz.api.blobs;

import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.BlobDAO;
import com.programowanie.zespolowe.pz.entities.Blob;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.FilteredBlobDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BlobManagement implements BlobAPI{

    Logger log = LoggerFactory.getLogger(BlobManagement.class);

    @Autowired
    CommonUtil commonUtil;
    @Autowired
    BlobDAO blobDAO;

    @Override
    public ResponseEntity create(@RequestBody MultipartFile file, @RequestParam("fileName") String fileName,
                                 @RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        try{
            Blob existingName = blobDAO.findByNameAndUser(fileName, user);
            if(existingName == null){
                persistBlob(fileName, user, file);
            } else {
                return commonUtil.getResponseEntity("Duplicated blob name.", HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            log.warn("Input stream fail.", e);
            return commonUtil.getResponseEntity("Malformed request body", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.warn("Unknown error", e);
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Blob created.", HttpStatus.OK);
    }

    public void persistBlob(String fileName, User user, MultipartFile file) throws IOException {
        Blob blob = new Blob();
        blob.setName(fileName);
        blob.setUser(user);
        blob.setData(IOUtils.toByteArray(file.getInputStream()));
        blobDAO.save(blob);
    }

    @Override
    public ResponseEntity getBlob(@PathVariable("blobId") String blobId){
        Blob blob = blobDAO.findById(Integer.parseInt(blobId)).get();
        Resource file = new ByteArrayResource(blob.getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }

    @Override
    public ResponseEntity getBlobsNamesAndIds(@RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        List<FilteredBlobDTO> test = blobDAO.getOnlyIdAndNameForUser(user);
        return commonUtil.getListResponseEntity(test, HttpStatus.OK);
    }

}


