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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @OneToOne
    private Address address;
    private String phoneNumber;
    private String emailAddress;
    @OneToOne
    private Photo photo;
    @OneToOne
    private User user;
}
