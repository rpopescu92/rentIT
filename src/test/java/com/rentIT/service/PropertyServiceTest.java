package com.rentIT.service;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.City;
import com.rentIT.domain.model.Property;
import com.rentIT.domain.model.User;
import com.rentIT.domain.repository.AddressRepository;
import com.rentIT.domain.repository.PropertyRepository;
import com.rentIT.domain.repository.UserRepository;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.exception.UserNotAuthenticatedException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
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
                .address(new Address("Brancoveanu","20","3", 3, new City("Bucharest")))
                .averageRating(0)
                .constructionYear(1992)
                .title("short")
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
                .address(new Address("Brancoveanu","20","3", 3,  new City("Bucharest")))
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
                .address(new Address("Brancoveanu","20","3", 3,  new City("Bucharest")))
                .build();
        propertyService.saveProperty(propertyDto);
    }

    @Test
    @Ignore
    public void testGetPropertiesByOwner() {
        User user = User.builder().username("ana").id(1).build();
        Mockito.when(userRepository.findByUsername("ana")).thenReturn(Optional.of(user));

        Property property = Property.builder().price(233).title("property1").longDescription("property1 description").address(new Address("street1","nr2")).build();
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        Page<Property> page = new PageImpl<Property>(properties,new PageRequest(1, 5, Sort.Direction.ASC, "+"), 1 );
        Mockito.when(propertyRepository.findPropertyByUserOwner(user, new PageRequest(1, 5, Sort.Direction.ASC, "+"))).thenReturn(page);

        Page<Property> pageResponse = propertyService.getPropertiesByOwner(String.valueOf(1), String.valueOf(5), "+","ana");
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(pageResponse.getContent().size(), 1);

    }

    @Test(expected = UserNotAuthenticatedException.class)
    public void testGetPropertiesWhenUserNotAuthenticated() {
        Mockito.when(userRepository.findByUsername("doesNotExists")).thenReturn(Optional.empty());

        Page<Property> page = propertyService.getPropertiesByOwner(String.valueOf(1), String.valueOf(5), "+","doesNotExists");
    }

    @Test
    @Ignore
    public void testGetAllProperties() {
        Property property = Property.builder().price(233).title("property1").longDescription("property1 description").address(new Address("street1","nr2")).build();
        List<Property> properties = new ArrayList<>();
        properties.add(property);
        Page<Property> page = new PageImpl<Property>(properties,new PageRequest(1, 5, Sort.Direction.ASC, "+"), 1 );
        Mockito.when(propertyRepository.findAllProperties(new PageRequest(1, 5, Sort.Direction.ASC, "+"))).thenReturn(page);

        Page<Property> propertiesResponse = propertyService.getAllProperties(1, 5, "+");
        Assert.assertNotNull(propertiesResponse);
        Assert.assertEquals(propertiesResponse.getTotalElements(), 1);
    }
}
