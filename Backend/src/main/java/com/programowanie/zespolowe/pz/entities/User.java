package com.programowanie.zespolowe.pz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userid;

    @Basic
    @Column(name = "email", nullable = false)
    private String email;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "surname")
    private String surname;

    //bi-directional many-to-one association to Blob
    @OneToMany(mappedBy = "user")
    @Basic
    @JsonIgnoreProperties({"user", "histories"})
    @Column(name = "blob")
    private List<Blob> blobs;

    //bi-directional many-to-one association to Device
    @OneToMany(mappedBy = "user")
    @Basic
    @JsonIgnoreProperties({"user", "histories"})
    @Column(name = "devices")
    private List<Device> devices;

    //bi-directional many-to-one association to Role
    @ManyToOne
    @Basic
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    @JsonIgnoreProperties("user")
    private Role role;

    @OneToMany(mappedBy = "user")
    @Basic
    @JsonIgnoreProperties({"user", "device", "blob"})
    @JsonIgnore
    @Column(name = "histories")
    private List<History> histories;

    public User() {
    }

    public List<History> getHistories() {
        return this.histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Blob> getBlobs() {
        return this.blobs;
    }

    public void setBlobs(List<Blob> blobs) {
        this.blobs = blobs;
    }

    public List<Device> getDevices() {
        return this.devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}