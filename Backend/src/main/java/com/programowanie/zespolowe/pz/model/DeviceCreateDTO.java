package com.programowanie.zespolowe.pz.model;

import javax.validation.constraints.NotNull;

public class DeviceCreateDTO {
    @NotNull
    private String macAdress;
    @NotNull
    private String name;

    public String getMacAdress() {
        return macAdress;
    }

    public void setMacAdress(String macAdress) {
        this.macAdress = macAdress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
