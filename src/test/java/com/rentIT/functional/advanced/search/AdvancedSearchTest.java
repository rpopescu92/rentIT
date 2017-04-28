package com.rentIT.functional.advanced.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentIT.functional.SpringIntegrationTest;
import com.rentIT.functional.authenticate.AuthenticateService;
import com.rentIT.functional.cleanup.CleanupService;
import com.rentIT.functional.register.RegisterUserService;
import com.rentIT.util.JwtToken;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.IOException;

public class AdvancedSearchTest extends SpringIntegrationTest {

    private final RegisterUserService registerUserService;
    private final AuthenticateService authenticateService;
    private final ManagePropertyService managePropertyService;
    private final CleanupService cleanupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseEntity response;
    private JwtToken jwtToken;

    @Autowired
    public AdvancedSearchTest(RegisterUserService registerUserService,
                              AuthenticateService authenticateService,
                              ManagePropertyService managePropertyService,
                              CleanupService cleanupService) {
        this.registerUserService = registerUserService;
        this.authenticateService = authenticateService;
        this.managePropertyService = managePropertyService;
        this.cleanupService = cleanupService;
    }

    @When("^A new user is registered$")
    public void a_new_user_is_registered() throws IOException {
        registerUserService
                .registerUser("turnball", "test1234", String.class);
    }

    @And("^The user authenticates$")
    public void and_the_user_authenticates() throws IOException {
        ResponseEntity<String> loginResponse = authenticateService.authenticate("turnball", "test1234", String.class);
        JwtToken jwtToken = objectMapper.readValue(loginResponse.getBody(), JwtToken.class);
        this.jwtToken = jwtToken;
    }

    @When("^The user creates some properties$")
    public void user_creates_some_properties() {
        managePropertyService.addProperty(this.jwtToken.getIdToken(), AdvancedSearchPropertyBuilder.createProperty("simple-title"));
    }

    @And("^The user searches for a property by title$")
    public void user_searches_for_a_property_by_title() {
        ResponseEntity response = managePropertyService.searchPropertyByTitle(this.jwtToken.getIdToken(), "simple-title", 1,3, "+");
        Assert.isTrue(response.getStatusCodeValue() == 200);
        this.response = response;
        cleanupService.cleanupProperty("turnball");
    }

    @Then("^The user should receive the expected result$")
    public void test() {
        System.out.println(this.response);
    }

}
