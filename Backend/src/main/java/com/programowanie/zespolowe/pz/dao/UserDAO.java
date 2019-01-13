package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.FilteredUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {

    @Query
    User findByEmail(String email);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredUserDTO(u.userid, u.email, u.name, u.surname) " +
            "FROM User u WHERE user = ?1")
    public List<FilteredUserDTO> getFilteredUser(User user);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredUserDTO(u.userid, u.email, u.name, u.surname) " +
            "FROM User u WHERE u.name LIKE ?1 OR u.surname LIKE ?2 OR u.name LIKE ?2 OR u.surname LIKE ?1")
    public List<FilteredUserDTO> getUserByNameAndSurname(String userName, String surName);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredUserDTO(u.userid, u.email, u.name, u.surname) " +
            "FROM User u WHERE u.name LIKE ?1 OR u.surname LIKE ?1")
    public List<FilteredUserDTO> getUserByNameAndSurname(String userName);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredUserDTO(u.userid, u.email, u.name, u.surname) " +
            "FROM User u")
    public List<FilteredUserDTO> getAllUsers();
}
