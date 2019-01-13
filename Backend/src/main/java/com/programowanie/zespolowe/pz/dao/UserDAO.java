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
}
