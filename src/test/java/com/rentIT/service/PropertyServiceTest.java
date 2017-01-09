package com.rentIT.service;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PropertyServiceTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;

    @Test
    public void testNewPropertyAdded() {
        User user = new User();
        user.setUsername("ana");
        Mockito.when(userRepository.findByUsername("ana")).thenReturn(Optional.of(user));

        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address("Brancoveanu","20","3", 3, "Bucharest"))
                .averageRating(0)
                .constructionYear(1992)
                .shortDescription("short")
                .isFurnished(true)
                .build();

        propertyService.saveProperty(propertyDto);
        Mockito.verify(userRepository,Mockito.times(1)).findByUsername("ana");

    }

    @Test(expected = InvalidPropertyException.class)
    public void testNewPropertyInvalidException() {
        User user = new User();
        user.setUsername("ana");
        Mockito.when(userRepository.findByUsername("ana")).thenReturn(Optional.of(user));

        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address("Brancoveanu","20","3", 3, "Bucharest"))
                .averageRating(0)
                .constructionYear(1992)
                .isFurnished(true)
                .build();

        propertyService.saveProperty(propertyDto);
        Mockito.verify(userRepository,Mockito.times(1)).findByUsername("ana");
    }

    @Test(expected = UserNotAuthenticatedException.class)
    public void testAddNewPropertyUserNotAuthenticatedException() {
        Mockito.when(userRepository.findByUsername("ana")).thenReturn(Optional.empty());
        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address("Brancoveanu","20","3", 3, "Bucharest"))
                .build();
        propertyService.saveProperty(propertyDto);
    }
}
