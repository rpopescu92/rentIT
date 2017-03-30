package com.rentIT.dto;

import com.rentIT.domain.model.UserRole;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable{

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isOwner;
    private boolean isTenant;
    private UserRole role;

}
