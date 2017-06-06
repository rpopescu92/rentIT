package com.rentIT.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class JwtTokenHolder {
    private JwtToken jwtToken;
}
