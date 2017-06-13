package com.rentIT.functional.register;

import com.rentIT.domain.model.UserRole;
import com.rentIT.dto.UserDto;
import com.rentIT.functional.cleanup.CleanupService;
import com.rentIT.util.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegisterUserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PropertyReader propertyReader;

    @Autowired
    private CleanupService cleanupService;

    public <T> ResponseEntity<T> registerUser(String userName, String password, Class<T> clazz) {
        UserDto newUser = new UserDto("test", "test", userName, password, "email@gmail.com", "213211", false, false, UserRole.USER);
        String registerUrl = propertyReader.getDevUrl() + "/register";
        return restTemplate.postForEntity(registerUrl, newUser, clazz);
    }

    public void cleanUp(String userName) {
        cleanupService.cleanupUser(userName);
    }


}
