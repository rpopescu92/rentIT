package com.rentIT.functional.advanced.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentIT.functional.SpringIntegrationTest;
import com.rentIT.functional.authenticate.AuthenticateService;
import com.rentIT.functional.cleanup.CleanupService;
import com.rentIT.functional.model.PageImplBean;
import com.rentIT.functional.register.RegisterUserService;
import com.rentIT.resource.model.SearchOptions;
import com.rentIT.util.JwtToken;
import com.rentIT.util.JwtTokenHolder;
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
    private final JwtTokenHolder jwtTokenHolder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseEntity<String> response;
    private JwtToken jwtToken;

    @Autowired
    public AdvancedSearchTest(RegisterUserService registerUserService,
                              AuthenticateService authenticateService,
                              ManagePropertyService managePropertyService,
                              CleanupService cleanupService,
                              JwtTokenHolder jwtTokenHolder) {
        this.registerUserService = registerUserService;
        this.authenticateService = authenticateService;
        this.managePropertyService = managePropertyService;
        this.cleanupService = cleanupService;
        this.jwtTokenHolder = jwtTokenHolder;
    }

    @And("^The user authenticates$")
    public void and_the_user_authenticates() throws IOException {
        ResponseEntity<String> loginResponse = authenticateService.authenticate("turnball", "test1234", String.class);
        JwtToken jwtToken = objectMapper.readValue(loginResponse.getBody(), JwtToken.class);
        this.jwtTokenHolder.setJwtToken(jwtToken);
    }

    @When("^A new user is registered$")
    public void a_new_user_is_registered() throws IOException {
        registerUserService
                .registerUser("turnball", "test1234", String.class);
    }

    @When("^The user creates only one properties$")
    public void user_creates_some_properties() {
        ResponseEntity<String> response = managePropertyService.addProperty(this.jwtTokenHolder.getJwtToken().getIdToken(), AdvancedSearchPropertyBuilder.createProperty("simple-title", 100));
        Assert.isTrue(response.getStatusCodeValue() == 200);
    }

    @And("^The user searches for that property by title$")
    public void user_searches_for_a_property_by_title() {
        ResponseEntity<String> response = managePropertyService.searchPropertyByTitle(this.jwtTokenHolder.getJwtToken().getIdToken(), "simple-title", 1, 3, "+");
        Assert.isTrue(response.getStatusCodeValue() == 200);
        this.response = response;
        cleanupService.cleanupProperty("turnball");
    }

    @Then("^The user should get only one property$")
    public void user_should_get_one_property() throws IOException {
        Assert.isTrue(this.response.getStatusCodeValue() == 200);
        PageImplBean properties = objectMapper.readValue(this.response.getBody(), PageImplBean.class);
        Assert.isTrue(properties != null);
        Assert.isTrue(!properties.getContent().isEmpty() && properties.getContent().size() == 1);
    }

    @When("^The user creates 10 properties$")
    public void user_creates_10_properties() {
        JwtToken jwtToken = this.jwtTokenHolder.getJwtToken();
        managePropertyService.addProperty(jwtToken.getIdToken(), AdvancedSearchPropertyBuilder.createProperties(10));
    }

    @And("^The user searches for properties with price between 10 and 70$")
    public void user_searches_3_properties_by_title() {
        JwtToken jwtToken = this.jwtTokenHolder.getJwtToken();
        SearchOptions searchOptions = AdvancedSearchPropertyBuilder.createSearchOptions(10,70);
        ResponseEntity<String> response = managePropertyService.searchProperty(jwtToken.getIdToken(), searchOptions,
                1, 10, "+");
        Assert.isTrue(response.getStatusCodeValue() == 200);
        this.response = response;
        cleanupService.cleanupProperty("turnball");
    }

    @Then("The user should receive 3 properties")
    public void user_should_receive_properties() throws IOException {
        PageImplBean properties = objectMapper.readValue(this.response.getBody(), PageImplBean.class);
        System.out.println(properties.getContent().size());
    }

}
