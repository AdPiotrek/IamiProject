package com.programowanie.zespolowe.pz.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Basic
	@Column(name = "role_id")
	private int roleid;

	@Basic
	@Column(name = "role", nullable = false)
	private String role;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="role")
	@JsonIgnoreProperties("role")
	private List<User> users;

	public Role() {
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}