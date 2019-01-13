package com.programowanie.zespolowe.pz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the devicefamilies database table.
 * 
 */
@Entity
@Table(name="devicefamilies")
public class Devicefamily implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic
	private int idDeviceFamilies;

	@ManyToOne
	@JsonIgnore
	private User user;

	@Basic
	@Column(name="family_name")
	private String familyName;

	//bi-directional many-to-many association to Device
	@ManyToMany
	@JoinTable(
		name="device_has_devicefamilies"
		, joinColumns={
			@JoinColumn(name="deviceFamilies_idDeviceFamilies")
			}
		, inverseJoinColumns={
			@JoinColumn(name="device_deviceid")
			}
		)
	private List<Device> devices;

	public Devicefamily() {
	}

	public int getIdDeviceFamilies() {
		return this.idDeviceFamilies;
	}

	public void setIdDeviceFamilies(int idDeviceFamilies) {
		this.idDeviceFamilies = idDeviceFamilies;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}