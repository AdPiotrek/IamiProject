package com.programowanie.zespolowe.pz.api.blobs;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Api do zarządzania plikami
 */
@RequestMapping("/blob")
public interface BlobAPI {

    /**
     * Pozawala dodać plik do bazy
     *
     * @param file     - plik
     * @param fileName - nazwa pliku
     * @param headers  - nagłówek
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity create(@RequestBody MultipartFile file,
                          @RequestParam("fileName") String fileName,
                          @RequestParam("description") String description,
                          @RequestParam("localizationUF") String localizationUF,
                          @RequestParam("latitude") BigDecimal latitude,
                          @RequestParam("longtitude") BigDecimal longtitude,
                          @RequestParam("date") @DateTimeFormat(pattern="yyyy-mm-dd") Date date,
                          @RequestParam("time") String time,
                          @RequestHeader HttpHeaders headers);

    /**
     * Pobiera plik o podanym id.
     *
     * @param blobId - identyfikator pliku
     */
    @RequestMapping(value = "/{blobId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    ResponseEntity getBlob(@PathVariable(value = "blobId") String blobId);

    /**
     * Pobiera wszystkie pliki
     *
     * @param headers - nagłówek
     */
    @RequestMapping(value = "/user/{userId}/{pageNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getBlobsNamesAndIdsForUser(@PathVariable(value = "userId") int userId,
                                       @PathVariable(value = "pageNumber") int pageNumber,
                                       @RequestHeader HttpHeaders headers);

    @RequestMapping(value = "/get/{pageNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getAllBlobs(@PathVariable(value = "pageNumber") int pageNumber);
}
