package com.rentIT.resource;

import com.rentIT.service.UserService;
import org.apache.el.stream.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
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

    }

    @Test
    public void testWhenNoAccount() {

        Mockito.when(userService.getAuthenticatedUser()).thenReturn(null);
        ResponseEntity responseEntity = accountResource.getAccount();

        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND));
        Assert.assertTrue(responseEntity.getBody().equals(null));
    }
}
