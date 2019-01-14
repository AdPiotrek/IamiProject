package com.programowanie.zespolowe.pz.api.user_management;

import com.programowanie.zespolowe.pz.model.UserRegisterDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Api do zarządzania użytkownikiem.
 */
@Controller
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

    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity getUserById(@PathVariable(value = "userId") int userId);

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity getAllUsers(@RequestParam(value = "name", required=false, defaultValue = "") String name, @RequestHeader HttpHeaders headers);

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity editUser(@RequestParam(value = "name", required=false, defaultValue = "") String name,
                            @RequestParam(value = "surname", required=false, defaultValue = "") String surname,
                            @RequestParam(value = "email", required=false, defaultValue = "") String email,
                            @RequestParam(value = "password", required=false, defaultValue = "") String password,
                            @RequestHeader HttpHeaders headers);

}
