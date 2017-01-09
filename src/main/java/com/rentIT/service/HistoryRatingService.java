package com.rentIT.service;

import com.rentIT.domain.model.HistoryRating;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.repository.HistoryRatingRepository;
import com.rentIT.domain.repository.PropertyRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HistoryRatingService {

    @Autowired
    private HistoryRatingRepository historyRatingRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    private Logger logger = LoggerFactory.getLogger(HistoryRatingService.class);

    public HistoryRating addNewRating(HistoryRating historyRating) {

        return historyRatingRepository.save(historyRating);
    }

    private float calculateRatingAverage(HistoryRating historyRating) {
        int count = historyRatingRepository.countAllRatingsForProperty(historyRating.getProperty());
        float pastRating = historyRating.getProperty().getAverageRating();
        float currentRating = (pastRating + historyRating.getRating())/count;
        historyRating.getProperty().setAverageRating(currentRating);

        return currentRating;
    }
}
