package com.rentIT.resource;

import com.rentIT.domain.model.HistoryRating;
import com.rentIT.domain.model.Property;
import com.rentIT.service.HistoryRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HistoryRatingResource {

    @Autowired
    private HistoryRatingService historyRatingService;

    @RequestMapping(value = "/ratings/{propertyId}", method = RequestMethod.POST)
    public ResponseEntity saveRating(@PathVariable("propertyId") long propertyId, @RequestBody HistoryRating historyRating){
        historyRatingService.addNewRating(historyRating);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/ratings/{propertyId}", method = RequestMethod.GET)
    public ResponseEntity<List<Property>> getHistoryRatings(@PathVariable("propertyId") long propertyId) {
        List<HistoryRating> ratings = historyRatingService.getHistoryRatings(propertyId);

        return new ResponseEntity(ratings, HttpStatus.OK);
    }
}
