package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceDAO extends JpaRepository<Device,Integer> {

    Device findByNameAndUser(String name, User user);

    Device findByMacAdressAndUser(String name, User user);

    List<Device> findByUser(User user);

}
