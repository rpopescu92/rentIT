package com.rentIT.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class City implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String region;

    @Column(name = "city_name")
    @JsonProperty("cityName")
    private String cityName;

    @JsonIgnore
    private String country;

//    public City(String cityName) {
//        this.cityName = cityName;
//    }
}
