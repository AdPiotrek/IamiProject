package com.programowanie.zespolowe.pz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the device database table.
 * 
 */
@Entity
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int deviceid;

	@Column(name="mac_adress")
	private String macAdress;


	private String name;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JsonIgnore
	private User user;

	//bi-directional many-to-many association to Devicefamily
	@ManyToMany(mappedBy="devices")
	@JsonIgnore
	private List<Devicefamily> devicefamilies;

	public Device() {
	}

	public int getDeviceid() {
		return this.deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	public String getMacAdress() {
		return this.macAdress;
	}

	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Devicefamily> getDevicefamilies() {
		return this.devicefamilies;
	}

	public void setDevicefamilies(List<Devicefamily> devicefamilies) {
		this.devicefamilies = devicefamilies;
	}

}