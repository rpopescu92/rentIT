package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user_details")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String streetName;
    private String streetNumber;
    private String phoneNumber;
    private String emailAddres;
    private String city;

}
