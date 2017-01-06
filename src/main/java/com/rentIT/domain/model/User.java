package com.rentIT.domain.model;


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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    private UserRole role;
    private boolean isOwner;
    private boolean isTenant;
    private Date createDate;

    public User(String username) {
        this.username = username;
    }

}
