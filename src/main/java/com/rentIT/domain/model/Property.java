package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "property")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String shortDescription;
    private String longDescription;

    @OneToOne
    private User owner;
    @OneToOne
    private User tenant;
    @OneToMany
    private List<HistoryRating> historyRatings;
    @OneToOne
    private Address address;

    private float averageRating;
    private float price;
    private int constructionYear;
    private int roomsNumber;
    private boolean isFurnished;
    @OneToMany
    private List<Image> images;
}
