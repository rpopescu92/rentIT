package com.rentIT.service;

import com.rentIT.domain.model.HistoryRating;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.HistoryRatingRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HistoryRatingService {

    @Autowired
    private HistoryRatingRepository historyRatingRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(HistoryRatingService.class);

    public HistoryRating addNewRating(HistoryRating historyRating) {
        if(historyRating.getProperty()!= null) {
            Property property = propertyRepository.findOne(historyRating.getProperty().getId());
            property.setAverageRating(calculateRatingAverage(historyRating));
            propertyRepository.save(property);
        } else if(historyRating.getUser() != null) {
            User user = userRepository.findOne(historyRating.getUser().getId());
            user.setAverageRating(calculateRatingAverage(historyRating));
            userRepository.save(user);
        }
        historyRating.setCreatedDate(new Date());
        return historyRatingRepository.save(historyRating);
    }

    private float calculateRatingAverage(HistoryRating historyRating) {
        int count = historyRatingRepository.countAllRatingsForProperty(historyRating.getProperty());
        float pastRating = historyRating.getProperty().getAverageRating();
        float currentRating = (count == 0) ? (pastRating + historyRating.getRating())/1 : (pastRating + historyRating.getRating())/count;
        historyRating.getProperty().setAverageRating(currentRating);

        return currentRating;
    }

    public List<HistoryRating> getHistoryRatings(long propertyId) {
        return historyRatingRepository.findAllPropertyRatings(propertyId);
    }
}
