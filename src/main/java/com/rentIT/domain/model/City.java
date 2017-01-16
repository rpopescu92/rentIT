package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;
    private String region;
    private String city;
    private String country;

    public City(String city) {
        this.city = city;
    }
}
