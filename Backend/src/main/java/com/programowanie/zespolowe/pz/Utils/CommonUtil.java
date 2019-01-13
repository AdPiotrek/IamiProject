package com.programowanie.zespolowe.pz.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.programowanie.zespolowe.pz.dao.UserDAO;
import com.programowanie.zespolowe.pz.entities.User;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.programowanie.zespolowe.pz.authapi.security.SecurityConstants.*;

@Component
public class CommonUtil {

    static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    @Autowired
    UserDAO userDAO;

    private Gson gson = new Gson();

    public User getUserFromHeader(HttpHeaders header){
        try {
            String token = header.get(HEADER_STRING).get(0);
            String userEmail = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            return userDAO.findByEmail(userEmail);
        }catch (Exception e){
            log.warn("Exception parsing token", e);
            return null;
            //TODO throw user not found exception and handle it globally to avoid checking if user is null in controller
        }
    }

    private String getAsJsonResult(String s){
        JsonObject jo = new JsonObject();
        jo.addProperty("result", s);
        return gson.toJson(jo);
    }

    private String getAsJsonResult(List s){
        return gson.toJson(s);
    }

    public ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus status){
        return new ResponseEntity<>(getAsJsonResult(responseMessage), status);
    }

    public ResponseEntity<String> getListResponseEntity(List list, HttpStatus status){
        return new ResponseEntity<>(getAsJsonResult(list), status);
    }

}
