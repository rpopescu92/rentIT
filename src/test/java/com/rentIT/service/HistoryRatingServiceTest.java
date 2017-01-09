package com.rentIT.service;

import com.rentIT.domain.model.HistoryRating;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.HistoryRatingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class HistoryRatingServiceTest {

    @InjectMocks
    private HistoryRatingService historyRatingService;

    @Mock
    private HistoryRatingRepository historyRatingRepository;

    @Test
    public void addNewRating() {
        HistoryRating historyRating = HistoryRating.builder()
                                                .rating(4)
                                                .comment("Very nice")
                                                .property(new Property("property1", new User("ana"))).build();
        Mockito.when(historyRatingRepository.save(historyRating)).thenReturn(historyRating);

        HistoryRating savedRating = historyRatingService.addNewRating(historyRating);
        Assert.assertNotNull(savedRating);
        Assert.assertEquals(savedRating.getRating(), historyRating.getRating());
    }
}
