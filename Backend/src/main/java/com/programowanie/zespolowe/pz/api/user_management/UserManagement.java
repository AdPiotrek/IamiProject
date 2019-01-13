package com.programowanie.zespolowe.pz.api.user_management;

import com.programowanie.zespolowe.pz.Utils.CommonUtil;
import com.programowanie.zespolowe.pz.dao.RoleDAO;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.User;
import com.programowanie.zespolowe.pz.model.FilteredUserDTO;
import com.programowanie.zespolowe.pz.model.UserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Api do zarządzania użytkownikami.
 */
@RestController
public class UserManagement implements UserManagementAPI {

    Logger log = LoggerFactory.getLogger(UserManagement.class);

    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    CommonUtil commonUtil;

    /**
     * @inheritDoc
     * @param userModel - model z danymi użytkownika do rejestracji.
     * @return Zwraca wiadomośc i odpowiedni status odpowiedzi.
     * @see HttpStatus#CONFLICT w przypadku gdy użytkownik istnieje.
     * @see HttpStatus#INTERNAL_SERVER_ERROR gdy wystąpił błąd serwera w czasie tworzenia użytkownika
     * @see HttpStatus#OK Tworzenie użytkownika zakończone sukcesem
     */
    @Override
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity register(@RequestBody UserRegisterDTO userModel) {
        if (userDAO.findByEmail(userModel.getEmail()) != null) {
            return commonUtil.getResponseEntity("User already exists.", HttpStatus.CONFLICT);
        } else {
            try {
                persistUser(userModel);
            } catch (Exception e) {
                return commonUtil.getResponseEntity("Server error.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return commonUtil.getResponseEntity("User created", HttpStatus.OK);
    }

    private void persistUser(UserRegisterDTO userModel) {
        User user = new User();
        user.setSurname(userModel.getLastName());
        user.setName(userModel.getFirstName());
        String passwordHashed = new BCryptPasswordEncoder().encode(userModel.getPassword());
        user.setPassword(passwordHashed);
        user.setEmail(userModel.getEmail());
        user.setRole(roleDAO.findByRole("user"));
        userDAO.save(user);
    }

    /**
     * Zwraca aktualnie zalogowanego użytkownika.
     *
     * @param headers nagłówek
     * @return Zwraca użytkownika lub wiadomośc z odpowiednim statusem.
     * @see HttpStatus#NOT_FOUND gdy użytkonik nie jest zalogowany lub w nagłówku nie ma tokenu z którego można wyciągnąć informację i użytkowniku
     */
    @Override
    public ResponseEntity getUser(@RequestHeader HttpHeaders headers) {
        User user = commonUtil.getUserFromHeader(headers);
        if (user == null) {
            return commonUtil.getResponseEntity("User not found.", HttpStatus.NOT_FOUND);
        }
        FilteredUserDTO filteredUserDTO = new FilteredUserDTO(user.getUserid(), user.getEmail(), user.getName(), user.getSurname());
        return ResponseEntity.status(HttpStatus.OK).body(filteredUserDTO);
    }

    @Override
    public ResponseEntity getAllUsers(@RequestParam("name") String name, @RequestHeader HttpHeaders headers) {
        List<FilteredUserDTO> users;
        if(name == null || name.isEmpty()){
            users = userDAO.getAllUsers();
        } else {
            String [] splitedName = name.split(" ");
            log.info(splitedName[0]);
            if(splitedName.length == 1){
                users = userDAO.getUserByNameAndSurname(splitedName[0]);
            } else {
                users = userDAO.getUserByNameAndSurname(splitedName[0], splitedName[1]);
            }
            if(users.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(users);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Override
    public ResponseEntity getUserById(@PathVariable(value = "userId") int userId){
        Optional<User> user = userDAO.findById(userId);
        if(user.isPresent()) {
            User userGet = user.get();
            FilteredUserDTO filteredUserDTO = new FilteredUserDTO(userGet.getUserid(), userGet.getEmail(), userGet.getName(), userGet.getSurname());
            return ResponseEntity.status(HttpStatus.OK).body(filteredUserDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity editUser(@RequestParam(value = "name", required=false, defaultValue = "") String name,
                                   @RequestParam(value = "surname", required=false, defaultValue = "") String surname,
                                   @RequestParam(value = "email", required=false, defaultValue = "") String email,
                                   @RequestParam(value = "password", required=false, defaultValue = "") String password,
                                   @RequestHeader HttpHeaders headers){
//        Optional<User> user = userDAO.findById();
//        if(user.isPresent()) {
//            User userGet = user.get();
//            FilteredUserDTO filteredUserDTO = new FilteredUserDTO(userGet.getUserid(), userGet.getEmail(), userGet.getName(), userGet.getSurname());
//            return ResponseEntity.status(HttpStatus.OK).body(filteredUserDTO);
//        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
    }


}
