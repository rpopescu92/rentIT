package com.rentIT.domain.model;

import lombok.*;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

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
    @GeneratedValue
    private long Id;

    private String streetName;
    private String streetNumber;
    private String apartmentNumber;
    private int floorNumber;

    @OneToOne
    private City city;

    public Address(String streetName, String streetNumber,
                   String apartmentNumber, int floorNumber, City city) {
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
