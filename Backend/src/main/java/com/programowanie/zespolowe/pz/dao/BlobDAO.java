package com.programowanie.zespolowe.pz.dao;

import com.programowanie.zespolowe.pz.entities.Blob;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.AlmostFullBlobDTO;
import com.programowanie.zespolowe.pz.model.FilteredBlobDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlobDAO extends JpaRepository<Blob,Integer> {

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.FilteredBlobDTO(b.blobid, b.name) FROM Blob b WHERE user = ?1")
    public List<FilteredBlobDTO> getOnlyIdAndNameForUser(User user);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.AlmostFullBlobDTO(b.blobid, b.name, b.description, b.localizationUF, b.latitude, " +
            "b.longtitude, b.user.name, b.user.surname, b.user.userid, b.date, b.time) FROM Blob b")
    public List<AlmostFullBlobDTO> getAlmostFullBlob(Pageable pageable);

    @Query(value ="SELECT new com.programowanie.zespolowe.pz.model.AlmostFullBlobDTO(b.blobid, b.name, b.description, b.localizationUF, b.latitude, " +
            "b.longtitude, b.user.name, b.user.surname, b.user.userid, b.date, b.time) FROM Blob b WHERE user.userid = ?1")
    public List<AlmostFullBlobDTO> getAlmostFullBlobForUser(Pageable pageable, int userId);

    Blob findByNameAndUser(String name, User u);

    Blob findByBlobidAndUser(int blobid, User u);

}
