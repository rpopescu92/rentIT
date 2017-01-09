package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "history_rating")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int rating;
    private String comment;

    @ManyToOne
    private Property property;

    @ManyToOne
    private User user;

}
