package com.programowanie.zespolowe.pz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the blobs database table.
 * 
 */
@Entity
@Table(name="blobs")
public class Blob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int blobid;

	@Lob
	@Column(unique = true)
	private byte[] data;

	private String name;
	private String description;
	private String localizationUF;
	@Column(precision = 10, scale = 8)
	private BigDecimal latitude;
	@Column(precision = 11, scale = 8)
	private BigDecimal longtitude;
	private Date date;
	private String time;


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "userid", nullable = false)
	@JsonIgnoreProperties({"blob", "blobs"})
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "blob")
	@Basic
	@JsonIgnoreProperties({"blob", "blobs"})
	@Column(name = "histories")
	@JsonIgnore
	private List<History> histories;

	public Blob() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getBlobid() {
		return blobid;
	}

	public void setBlobid(int blobid) {
		this.blobid = blobid;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocalizationUF() {
		return localizationUF;
	}

	public void setLocalizationUF(String localizationUF) {
		this.localizationUF = localizationUF;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(BigDecimal longtitude) {
		this.longtitude = longtitude;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<History> getHistories() {
		return histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}
}