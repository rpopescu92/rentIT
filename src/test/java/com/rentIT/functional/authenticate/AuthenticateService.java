package com.rentIT.functional.authenticate;

import com.rentIT.dto.AuthenticationDto;
import com.rentIT.util.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticateService {

    private final PropertyReader propertyReader;

    private final RestTemplate restTemplate;

    @Autowired
    public AuthenticateService(PropertyReader propertyReader, RestTemplate restTemplate) {
        this.propertyReader = propertyReader;
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> authenticate(String userName, String password, Class<T> clazz) {
        AuthenticationDto authentication = new AuthenticationDto(userName, password);
        String loginUrl = propertyReader.getDevUrl() + "/api/authenticate";
        return restTemplate.postForEntity(loginUrl, authentication, clazz);
    }


}
