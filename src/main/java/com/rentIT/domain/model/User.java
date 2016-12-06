package com.rentIT.domain.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
    private boolean isOwner;
    private boolean isTenant;
    private UserDetails userDetails;

}
