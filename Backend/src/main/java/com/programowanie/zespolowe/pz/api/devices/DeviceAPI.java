package com.programowanie.zespolowe.pz.api.devices;

import com.programowanie.zespolowe.pz.entities.Device;
import com.programowanie.zespolowe.pz.model.DeviceCreateDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Api do zarządzania urządzeniami.
 */
@RequestMapping("/device")
public interface DeviceAPI {

    /**
     * Pozwala utworzyć urządzenie.
     *
     * @param device  - urzędzenie które ma być dodane dodane.
     * @param headers - nagłówek
     * @see DeviceCreateDTO#macAdress - adres mac urządzenia
     * @see DeviceCreateDTO#name - nazwa urządzenia
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity register(@RequestBody DeviceCreateDTO device, @RequestHeader HttpHeaders headers);

    /**
     * Pobiera listę urządzeń
     *
     * @param headers - nagłówek.
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getDevicesList(@RequestHeader HttpHeaders headers);

    /**
     * Usuwa urządzenie o podanym id.
     *
     * @param deviceId - identyfikator urządzenia.
     */
    @RequestMapping(value = "/delete/{deviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity deleteDevice(@PathVariable(value = "deviceId") String deviceId);

    /**
     * pobiera urządzenie o podanym identyfikatorze.
     *
     * @param deviceId - identyfikator urządzenia.
     */
    @RequestMapping(value = "/get/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getDevice(@PathVariable(value = "deviceId") String deviceId);

    /**
     * Edytuje urządzenie
     *
     * @param device urzędzenie powi
     * @see Device#deviceid powinno być podane po nim jest idnetyfikowane urządzenie które jest edytowane.
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity editDevice(@RequestBody Device device);

}
