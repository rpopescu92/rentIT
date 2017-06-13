package com.rentIT.resource;

import com.rentIT.domain.model.User;
import com.rentIT.domain.model.UserRole;
import com.rentIT.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Ignore
public class AccountResourceTest {

    @InjectMocks
    private AccountResource accountResource;

    @Mock
    private UserService userService;

    @Before
    public void setup() {
        standaloneSetup(accountResource).build();
    }

    @Test
    public void testWhenAccountsExists() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(Optional.of(User.builder().username("paul_giant").role(UserRole.USER).build()));
        ResponseEntity responseEntity = accountResource.getAccount();

        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        Assert.assertTrue(responseEntity.hasBody());
    }

    @Test
    public void testWhenNoAccount() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(Optional.empty());
        ResponseEntity responseEntity = accountResource.getAccount();

        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
        Assert.assertTrue(!responseEntity.hasBody());
    }
}
