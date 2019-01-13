package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.Blob;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.FilteredBlobDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlobDAO extends JpaRepository<Blob,Integer> {

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredBlobDTO(b.blobid, b.name) FROM Blob b WHERE user = ?1")
    public List<FilteredBlobDTO> getOnlyIdAndNameForUser(User user);

    Blob findByNameAndUser(String name, User u);

}
