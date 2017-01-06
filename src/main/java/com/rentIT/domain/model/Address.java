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
}
