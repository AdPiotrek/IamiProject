package com.programowanie.zespolowe.pz.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class BlobDTO {

    @NotNull
    private String blobName;
    @NotNull
    private MultipartFile multipartFile;

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
