package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "profile_details")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetails {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    @OneToOne
    private Address address;

    @OneToOne
    private Photo photo;

    @OneToOne
    private User user;
}
