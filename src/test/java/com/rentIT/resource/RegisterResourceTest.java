package com.rentIT.resource;

import com.rentIT.domain.model.User;
import com.rentIT.exception.UserExistsException;
import com.rentIT.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RegisterResourceTest {

    @InjectMocks
    private RegisterResource registerResource;

    @Mock
    private UserService userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UserExistsException.class)
    public void registerUserExistsException() {
        User userMock = User.builder().username("usernameExists").build();
        Mockito.doThrow(new UserExistsException("Username already exists")).when(userService).registerUser(userMock);

        ResponseEntity<User> responseEntity = registerResource.register(userMock);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void registerUser(){
        User userMock = User.builder().firstName("Paul").lastName("Giant").username("PaulGiant").build();

        ResponseEntity<User> responseEntity = registerResource.register(userMock);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
