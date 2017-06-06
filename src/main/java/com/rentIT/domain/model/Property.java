package com.rentIT.domain.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "property")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Property implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    @Type(type="text")
    private String longDescription;

    @OneToOne
    private User owner;

    @OneToOne
    private User tenant;

    @OneToOne
    private Address address;

    private float averageRating;
    private String title;
    private float price;
    private Currency currency;
    private int constructionYear;
    private int roomsNumber;
    private boolean isFurnished;
    private Boolean allowsPets;
    private boolean isRented;
    private Date dateAdded;
    private float floorArea;
    private String otherInfo;

    @ElementCollection(targetClass=Photo.class,fetch = FetchType.EAGER)
    private List<Photo> images;

    @OneToOne
    private Facilities facilities;

    public Property(String shortDescription, User owner) {
        this.title = shortDescription;
        this.owner = owner;
    }
}
