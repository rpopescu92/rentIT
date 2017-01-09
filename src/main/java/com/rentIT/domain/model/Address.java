package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String streetName;
    private String streetNumber;
    private String apartmentNumber;
    private int floorNumber;
    private String city;
    private String otherDirections;

    public Address(String streetName, String streetNumber,
                   String apartmentNumber, int floorNumber, String city) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.floorNumber = floorNumber;
        this.city = city;
    }
    public Address(String streetName, String streetNumber){
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }
}
