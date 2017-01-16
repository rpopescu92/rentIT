package com.rentIT.resource;

import com.rentIT.domain.model.Address;
import com.rentIT.domain.model.City;
import com.rentIT.domain.model.Property;
import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import com.rentIT.service.PropertyService;
import org.junit.Assert;
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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PropertyResourceTest {

    @InjectMocks
    private PropertyResource propertyResource;

    @Mock
    private PropertyService propertyService;

    @Test
    public void testAddProperty() {
        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address("Brancoveanu","20","3", 3, new City("Bucharest")))
                .averageRating(0)
                .constructionYear(1992)
                .isFurnished(true)
                .build();
        ResponseEntity responseEntity = propertyResource.addProperty(propertyDto);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAddPropertyBadRequest() {
        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address())
                .averageRating(0)
                .constructionYear(1992)
                .title("title")
                .isFurnished(true)
                .build();

        Mockito.doThrow(new InvalidPropertyException("Invalid fields. Address cannot be empty")).when(propertyService).saveProperty(propertyDto);
        ResponseEntity responseEntity = propertyResource.addProperty(propertyDto);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(responseEntity.getBody(), "Invalid fields. Address cannot be empty");
    }

    @Test
    @Ignore
    public void testAddPropertyRandomException() {
        PropertyDto propertyDto = PropertyDto.builder()
                .username("ana")
                .address(new Address())
                .averageRating(0)
                .constructionYear(1992)
                .title("short")
                .isFurnished(true)
                .build();

        Mockito.doThrow(new Exception()).when(propertyService).saveProperty(propertyDto);
        ResponseEntity responseEntity = propertyResource.addProperty(propertyDto);
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
