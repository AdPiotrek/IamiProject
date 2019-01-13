
package com.programowanie.zespolowe.pz.api.deviceFamily;

import com.programowanie.zespolowe.pz.entities.Devicefamily;
import com.programowanie.zespolowe.pz.model.CreateFamilyDeviceDTO;
import com.programowanie.zespolowe.pz.model.DeviceFamilyDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Api dostępowe do zarządzania grupami urządzeń.
 */
@RequestMapping("/deviceFamily")
public interface DeviceFamilyAPI {

    /**
     * Utworzenie grupy urządzeń
     *
     * @param CreateFamilyDeviceDTO model danych.
     * @param headers               nagłówek żądania dodawany do odpowiedzi
     * @see com.programowanie.zespolowe.pz.model.CreateFamilyDeviceDTO#familyName jest jedynym wymaganym polem.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity create(@Valid @RequestBody CreateFamilyDeviceDTO CreateFamilyDeviceDTO, @RequestHeader HttpHeaders headers);

    /**
     * Pobranie wszystkich grup
     *
     * @param headers nagłówek żądania dodawany do odpowiedzi
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getDevicesFamilyList(@RequestHeader HttpHeaders headers);

    /**
     * Usunięcie grupy o podanym id
     *
     * @param id indetyfikator grupy
     */
    @RequestMapping(value = "/delete/{familyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity deleteDeviceFamily(@PathVariable(value = "familyId") String id);

    /**
     * Pobranie grupy o podanym id
     *
     * @param deviceFamilyId - identyfikator grupy urządzeń
     * @param headers
     */
    @RequestMapping(value = "/get/{familyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getDeviceFamily(@PathVariable(value = "familyId") String deviceFamilyId,@RequestHeader HttpHeaders headers);

    /**
     * Pozwala na edycjęe grupy urządzeń
     *
     * @param devicefamily zeedetywane grupa urządzeń powinna posiadać id edytowanej grupy
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity editFamily(@RequestBody Devicefamily devicefamily);

    /**
     * Dodawanie urządzenia do grupy urządzeń.
     *
     * @param deviceFamilyDTO musi zawierac id urządzenia i id grupy do której ma być dodane.
     * @see DeviceFamilyDTO#deviceID identyfikator urządzenia.
     * @see DeviceFamilyDTO#familyID identyfikator grupy do które ma zostać dodane urządzenie.
     */
    @RequestMapping(value = "/addDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity addDeviceToFamily(@RequestBody DeviceFamilyDTO deviceFamilyDTO);

    /**
     * Usuwanie urządzenia z grupy urządzeń dla grupy o podanym id i urządzeń o podanym id.
     *
     */
    @RequestMapping(value = "/{familyId}/removeDevice/{deviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity removeDeviceToFamily(@PathVariable(value = "familyId") String familyId, @PathVariable(value = "deviceId") String deviceId, @RequestHeader HttpHeaders headers);
}