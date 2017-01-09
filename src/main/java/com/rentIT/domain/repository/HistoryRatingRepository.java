package com.rentIT.domain.repository;


import com.rentIT.domain.model.HistoryRating;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRatingRepository extends JpaRepository<HistoryRating, Long> {

    @Query("select hr from HistoryRating hr where hr.property=null and hr.user=?1")
    Page<HistoryRating> findAllUserRatings(User user, Pageable pageable);

    @Query("select hr from HistoryRating hr where hr.user=null and hr.property=?1")
    Page<HistoryRating> findAllPropertyRatings(Property property, Pageable pageable);

    @Query("select count(hr) from HistoryRating hr where hr.property=?1 and hr.rating=?2")
    Integer countSpecificRatingForProperty (Property property, int rating);

    @Query("select count(use) from HistoryRating hr where hr.user=?1 and hr.rating=?2")
    Integer countSpecificRatingForUser(User user, int rating);

    @Query("select count(hr) from HistoryRating hr where hr.property=?1")
    Integer countAllRatingsForProperty(Property property);
}
