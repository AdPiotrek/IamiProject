package com.programowanie.zespolowe.pz.Utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

/**
 * Statyczna klasa pomocnicza do operacji usuwania i pobierania elementu z bazy po id.
 */
public class DataBaseOperations {

    /**
     * Generyczna klasa pozwalający usuń rekord o podanym id dla podanej klasy dao.
     * @param id - Unikalny numer rekodu.
     * @param dao - Klasa dostępowa do tabeli.
     * @param commonUtil - Klasa pomocnicza
     * @param <T> - klasa rozszerzajaca JpaRepository.
     * @return Komunikato w formie Json z odpowiednik statusem HTTP.
     */
    public static <T extends JpaRepository> ResponseEntity deleteById(String id, T dao,CommonUtil commonUtil){
        try{
            dao.deleteById(Integer.parseInt(id));
        } catch (NumberFormatException n){
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return commonUtil.getResponseEntity("Device deleted.", HttpStatus.OK);
    }

    /**
     * Generyczna klasa pozwalający pobrać rekord o podanym id dla podanej klasy dao.
     * @param id - Unikalny numer rekodu.
     * @param dao - Klasa dostępowa do tabeli.
     * @param commonUtil - Klasa pomocnicza
     * @param <T> - klasa rozszerzajaca JpaRepository.
     * @return Komunikato w formie Json z odpowiednik statusem HTTP.
     */
    public static <T extends JpaRepository> ResponseEntity getById(String id, T dao,CommonUtil commonUtil){
        try{
            Object device = dao.findById(Integer.parseInt(id)).get();
            return ResponseEntity.status(HttpStatus.OK).body(device);
        } catch (NumberFormatException n){
            return commonUtil.getResponseEntity("Not a number.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e){
            return commonUtil.getResponseEntity("Device not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}