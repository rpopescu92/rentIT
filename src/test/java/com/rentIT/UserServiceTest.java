package com.rentIT;

import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        standaloneSetup(userService).build();
    }

    @Test
    public void testGetAccountWhenUserExists(){
        Mockito.when(userRepository.findByUsername("username")).thenReturn(new User("username"));

        User user = userService.getAuthenticatedUser();
        Assert.assertFalse(user.equals(null));
        Assert.assertTrue(user.getUsername().equals("username"));

    }
}
