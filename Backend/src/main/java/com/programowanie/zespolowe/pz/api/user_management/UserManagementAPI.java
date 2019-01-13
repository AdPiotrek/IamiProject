package com.programowanie.zespolowe.pz.api.user_management;

import com.programowanie.zespolowe.pz.model.UserRegisterDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Api do zarządzania użytkownikiem.
 */
@RequestMapping("/user")
public interface UserManagementAPI {

    /**
     * Rejestracja użytkownika
     *
     * @param userModel - model z danymi użytkownika do rejestracji.
     * @see UserRegisterDTO#email - adres email użytkownika po nim jest identyfikowany użytkownik i służy jako login do systemu.
     * @see UserRegisterDTO#firstName - pierwszę imię użytkownika.
     * @see UserRegisterDTO#lastName nazwisko użytkownika.
     * * @see UserRegisterDTO#password hasło użytkownika
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity register(@RequestBody UserRegisterDTO userModel);

    /**
     * Zwraca aktualnie zalogowanego użytkownika.
     *
     * @param headers nagłówek
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity getUser(@RequestHeader HttpHeaders headers);

}
