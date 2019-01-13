package com.programowanie.zespolowe.pz.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class FilteredBlobDTO {

    private int blobid;
    private String blobName;

    public FilteredBlobDTO(int blobid, String blobName) {
        this.blobid = blobid;
        this.blobName = blobName;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }

    public int getBlobid() {
        return blobid;
    }

    public void setBlobid(int blobid) {
        this.blobid = blobid;
    }
}
