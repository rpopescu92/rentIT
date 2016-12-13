package com.rentIT.service;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAccountWhenAuthenticationIsNull(){
        Optional<User> optional = userService.getAuthenticatedUser();
        Assert.assertEquals(null, optional);
    }

    @Test
    @Ignore
    public void testGetAccountWhenUserExists(){
        Authentication authentication = mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("username");

        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(new User("username")));

        Optional<User> optional = userService.getAuthenticatedUser();
        Assert.assertTrue(optional.isPresent());
        Assert.assertTrue(optional.get().getUsername().equals("username"));
    }

    @Test
    @Ignore
    public void testGetAccountWhenUserIsUnknown() {
        Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());

        Optional<User> optional = userService.getAuthenticatedUser();
        Assert.assertTrue(!optional.isPresent());
    }
}
