package com.rentIT.resource;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rentIT.domain.model.User;
import com.rentIT.dto.UserDto;
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

    @Test
    public void registerUserExistsException() {
        UserDto userMock = UserDto.builder().username("usernameExists").password("1234").build();
        Mockito.doThrow(new UserExistsException("Username already exists")).when(userService).registerUser(userMock);

        ResponseEntity responseEntity = registerResource.register(userMock);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        //Assert.assertEquals(responseEntity.getBody(), "Username already exists.");
    }

    @Test
    public void registerUser(){
        UserDto userMock = UserDto.builder().username("PaulGiant").password("1234").build();

        ResponseEntity<User> responseEntity = registerResource.register(userMock);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
