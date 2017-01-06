package com.rentIT.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    @OneToOne
    private Address address;
    private String phoneNumber;
    private String emailAddress;

    @OneToOne
    private User user;
}
