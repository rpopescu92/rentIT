package com.rentIT.functional.register;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.functional.SpringIntegrationTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class RegisterTest extends SpringIntegrationTest {

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private UserRepository userRepository;

    @When("^a new user is registered the status code should be 200$")
    public void when_a_new_user_is_registered() {
        ResponseEntity<String> response = registerUserService.registerUser("hapciu1", "test1234", String.class);
        Assert.isTrue(response.getStatusCodeValue() == 200);
    }

    @And("^a user should be created$")
    public void and_a_new_user_is_created() {
        User user = userRepository.findByUsername("hapciu1").orElseThrow(RuntimeException::new);
        Assert.notNull(user);
        registerUserService.cleanUp("hapciu1");
    }

}
