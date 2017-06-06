package com.rentIT.functional.advanced.search;

import com.rentIT.dto.PropertyDto;
import com.rentIT.resource.model.SearchOptions;
import com.rentIT.util.HttpHeaderFactory;
import com.rentIT.util.PropertyReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ManagePropertyService {

    private final RestTemplate restTemplate;
    private final PropertyReader propertyReader;

    @Autowired
    public ManagePropertyService(RestTemplate restTemplate, PropertyReader propertyReader) {
        this.restTemplate = restTemplate;
        this.propertyReader = propertyReader;
    }

    public ResponseEntity<String> addProperty(String jwtToken, PropertyDto propertyDto) {
        HttpEntity<PropertyDto> entity = new HttpEntity<>(propertyDto, HttpHeaderFactory.createAuthenticationHeader(jwtToken));
        return restTemplate.exchange(propertyReader.getDevUrl() + "/api/properties", HttpMethod.POST, entity, String.class);
    }

    public void addProperty(String jwtToken, List<PropertyDto> propertyDto) {
        propertyDto.forEach(p -> {
            HttpEntity<PropertyDto> entity = new HttpEntity<>(p, HttpHeaderFactory.createAuthenticationHeader(jwtToken));
            restTemplate.exchange(propertyReader.getDevUrl() + "/api/properties", HttpMethod.POST, entity, String.class);
        });
    }

    public ResponseEntity<String> searchPropertyByTitle(String jwtToken, String title, int page, int perPage, String order) {
        SearchOptions searchOptions = new SearchOptions();
        searchOptions.setName(title);
        HttpEntity<SearchOptions> entity = new HttpEntity<>(searchOptions, HttpHeaderFactory.createAuthenticationHeader(jwtToken));
        String url = "/api/properties/advanced?page=" + page + "&limit=" + perPage + "&order=" + order;
        return restTemplate.exchange(propertyReader.getDevUrl() + url, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> searchProperty(String jwtToken, SearchOptions searchOptions, int page, int perPage, String order) {
        HttpEntity<SearchOptions> entity = new HttpEntity<>(searchOptions, HttpHeaderFactory.createAuthenticationHeader(jwtToken));
        String url = "/api/properties/advanced?page=" + page + "&limit=" + perPage + "&order=" + order;
        return restTemplate.exchange(propertyReader.getDevUrl() + url, HttpMethod.POST, entity, String.class);
    }

}
