package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role,Integer> {

    @Query
    Role findByRole(String role);

}
