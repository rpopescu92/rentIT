package com.rentIT.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto implements Serializable{

    private String username;
    private String streetName;
    private String streetNumber;
    private String phoneNumber;
    private String emailAddress;
    private String city;
}
