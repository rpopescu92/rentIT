package com.rentIT.functional.register;

import com.rentIT.functional.SpringIntegrationTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RegisterTest extends SpringIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @When("^a new user is registered$")
    public void when_a_new_user_is_registered() {
        System.out.println(restTemplate.getForEntity("http://www.google.co.uk", String.class));
    }

    @Then("^the status code should be 200$")
    public void then_the_status_code_is_200() {

    }

    @And("^a user should be created$")
    public void and_a_new_user_is_created() {

    }

}
