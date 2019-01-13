package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.api.deviceFamily.DeviceFamilyManagement;
import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.entities.Devicefamily;
import com.programowanie.zespolowe.pz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceFamilyDAO extends JpaRepository<Devicefamily,Integer> {

    List<Devicefamily> findByUser(User user);
    boolean existsByFamilyNameAndUser(String familyName,User user);
    boolean existsByDevicesEqualsAndIdDeviceFamilies(Device device,int idDeviceFamilies);
}
