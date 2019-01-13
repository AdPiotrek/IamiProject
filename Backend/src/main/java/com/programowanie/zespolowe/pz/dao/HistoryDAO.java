package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.History;
import com.programowanie.zespolowe.pz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryDAO extends JpaRepository<History,Integer> {

    List<History> getByUser(User u);

}
