package com.rentIT.domain.repository;


import com.rentIT.domain.model.HistoryRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRatingRepository extends JpaRepository<HistoryRating, Long> {
}
