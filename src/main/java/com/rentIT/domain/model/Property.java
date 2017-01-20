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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Type(type="text")
    private String longDescription;

    @OneToOne
    private User owner;
    @OneToOne
    private User tenant;
    @OneToOne
    private Address address;

    private float averageRating;
    private float price;
    private int constructionYear;
    private int roomsNumber;
    private boolean isFurnished;
    private boolean isRented;
    private Date dateAdded;
    @ElementCollection(targetClass=String.class)
    private List<String> images;

    public Property(String shortDescription, User owner) {
        this.title = shortDescription;
        this.owner = owner;
    }
}
