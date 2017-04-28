package com.rentIT.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private UserRole role;

    @JsonIgnore
    private boolean isOwner;

    @JsonIgnore
    private boolean isTenant;

    @JsonIgnore
    private Date createDate;

    @JsonIgnore
    private float averageRating;

    public User(String username) {
        this.username = username;
    }

}
