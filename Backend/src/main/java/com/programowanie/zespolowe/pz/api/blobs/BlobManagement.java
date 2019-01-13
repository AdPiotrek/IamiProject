package com.programowanie.zespolowe.pz.api.blobs;

import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.BlobDAO;
import com.programowanie.zespolowe.pz.entities.Blob;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.AlmostFullBlobDTO;
import com.programowanie.zespolowe.pz.model.FilteredBlobDTO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
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
import java.math.BigDecimal;
import java.util.List;

@Controller
public class BlobManagement implements BlobAPI{

    Logger log = LoggerFactory.getLogger(BlobManagement.class);

    @Autowired
    CommonUtil commonUtil;
    @Autowired
    BlobDAO blobDAO;

    @Override
    public ResponseEntity create(@RequestBody MultipartFile file,
                                 @RequestParam("fileName") String fileName,
                                 @RequestParam("description") String description,
                                 @RequestParam("localizationUF") String localizationUF,
                                 @RequestParam("latitude") BigDecimal latitude,
                                 @RequestParam("longtitude") BigDecimal longtitude,
                                 @RequestHeader HttpHeaders headers){
        User user = commonUtil.getUserFromHeader(headers);
        if(user == null){
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        try{
            persistBlob(fileName, user, file, description, longtitude, latitude, localizationUF);
        } catch (IOException e) {
            log.warn("Input stream fail.", e);
            return commonUtil.getResponseEntity("Malformed request body", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.warn("Unknown error", e);
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Blob created.", HttpStatus.OK);
    }

    public void persistBlob(String fileName, User user, MultipartFile file, String desc,
                            BigDecimal longtitude, BigDecimal latitude, String localizationUF) throws IOException {
        Blob blob = new Blob();
        blob.setName(fileName);
        blob.setUser(user);
        blob.setData(IOUtils.toByteArray(file.getInputStream()));
        blob.setDescription(desc);
        blob.setLongtitude(longtitude);
        blob.setLatitude(latitude);
        blob.setLocalizationUF(localizationUF);
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

    @Override
    public ResponseEntity getAllBlobs(@PathVariable("pageNumber") int pageNumber){

        List<AlmostFullBlobDTO> test = blobDAO.getAlmostFullBlob(new PageRequest(pageNumber, 40));
        return commonUtil.getListResponseEntity(test, HttpStatus.OK);
    }

}


