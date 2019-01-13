package com.programowanie.zespolowe.pz.api.history;

import com.programowanie.zespolowe.pz.model.HistoryEntryDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/history")
public interface HistoryAPI {

    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity register(@RequestBody HistoryEntryDTO historyEntryDTO, @RequestHeader HttpHeaders headers);

    @RequestMapping(method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity getHistoryEventList(@RequestHeader HttpHeaders headers);

}
