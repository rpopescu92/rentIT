package com.rentIT.service;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.exception.UserExistsException;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

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

        User userMock = new User();
        userMock.setFirstName("Paul");
        userMock.setLastName("Grave");
        userMock.setUsername("PaulGrave");
        Optional<User> userOptional = Optional.of(userMock);
        Mockito.when(userRepository.findByUsername("username")).thenReturn(userOptional);

        Optional<User> optional = userService.getAuthenticatedUser();
        Assert.assertTrue(optional.isPresent());
        Assert.assertTrue(optional.get().getUsername().equals("username"));
    }

    @Test
    @Ignore
    public void testGetAccountWhenUserIsUnknown() {
        SecurityContextHolder securityContextHolder = Mockito.mock(SecurityContextHolder.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Optional<User> optional = Optional.empty();
        Mockito.when(userRepository.findByUsername("username")).thenReturn(optional);

        Optional<User> optionalResponse = userService.getAuthenticatedUser();
        Assert.assertTrue(optionalResponse.isPresent());
    }

    @Test(expected = UserExistsException.class)
    public void testRegisterUserWhenUserExists() {
        Mockito.when(userRepository.findByUsername("existsUsername")).thenReturn(Optional.of(new User("existsUsername")));

        User user = User.builder().username("existsUsername").build();
        userService.registerUser(user);
    }

    @Test
    public void testRegisterUser() {
        User userMock = new User();
        userMock.setFirstName("Paul");
        userMock.setLastName("Grave");
        userMock.setUsername("PaulGrave");
        Optional<User> optional = Optional.empty();

        Mockito.when(userRepository.findByUsername("PaulGrave")).thenReturn(optional);
        Mockito.when(userRepository.save(userMock)).thenReturn(userMock);

        userService.registerUser(userMock);
        Mockito.verify(userRepository, times(1)).findByUsername("PaulGrave");
        Mockito.verify(userRepository, times(1)).save(userMock);
    }
}
