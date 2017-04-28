package com.rentIT.functional.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentIT.dto.AuthenticationDto;
import com.rentIT.functional.SpringIntegrationTest;
import com.rentIT.functional.authenticate.AuthenticateService;
import com.rentIT.functional.register.RegisterUserService;
import com.rentIT.util.JwtToken;
import com.rentIT.util.PropertyReader;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class LogintTest extends SpringIntegrationTest {

    private final RestTemplate restTemplate;
    private final PropertyReader propertyReader;
    private final AuthenticateService authenticateService;
    private final RegisterUserService registerUserService;

    @Autowired
    public LogintTest(RestTemplate restTemplate, AuthenticateService authenticateService, PropertyReader propertyReader, RegisterUserService registerUserService) {
        this.restTemplate = restTemplate;
        this.registerUserService = registerUserService;
        this.propertyReader = propertyReader;
        this.authenticateService = authenticateService;
    }

    @When("^a non existent user tries to login then the status code should be 401$")
    public void when_an_inexistent_user_tries_to_login() {
        AuthenticationDto authentication = new AuthenticationDto("fake-name", "fake-password");
        String url = propertyReader.getDevUrl() + "/api/authenticate";
        ResponseEntity<String> response = restTemplate.postForEntity(url, authentication, String.class);
        Assert.isTrue(response.getStatusCodeValue() == 401);
    }

    @When("^an existent user tries to login then the status code should be 200$")
    public void when_an_existent_user_tries_to_login() {
//        registerUserService.cleanUp("hapciu1"); TODO FIX THIS
        ResponseEntity<String> response = registerUserService.registerUser("hapciu1", "test1234", String.class);
        Assert.isTrue(response.getStatusCodeValue() == 200);

        ResponseEntity<String> loginResponse = authenticateService.authenticate("hapciu1", "test1234", String.class);
        Assert.isTrue(loginResponse.getStatusCodeValue() == 200);

        registerUserService.cleanUp("hapciu1");
    }

    @When("^an user tries to login then get the authentication token$")
    public void when_an_user_tries_to_login_get_jwt_token() throws IOException {
        ResponseEntity<String> response = registerUserService.registerUser("hapciu1", "test1234", String.class);
        Assert.isTrue(response.getStatusCodeValue() == 200);

        ResponseEntity<String> loginResponse = authenticateService.authenticate("hapciu1", "test1234", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JwtToken jwtToken = objectMapper.readValue(loginResponse.getBody(), JwtToken.class);
        Assert.isTrue(loginResponse.getStatusCodeValue() == 200);
        Assert.isTrue(!jwtToken.getIdToken().isEmpty());

        registerUserService.cleanUp("hapciu1");
    }

}
