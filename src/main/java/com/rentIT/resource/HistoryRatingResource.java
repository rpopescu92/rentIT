package com.rentIT.resource;

import com.rentIT.domain.model.HistoryRating;
import com.rentIT.service.HistoryRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HistoryRatingResource {

    @Autowired
    private HistoryRatingService historyRatingService;

    @RequestMapping(value = "/ratings", method = RequestMethod.POST)
    public ResponseEntity saveRating(@RequestBody HistoryRating historyRating){
        historyRatingService.addNewRating(historyRating);

        return new ResponseEntity(HttpStatus.OK);
    }
}
