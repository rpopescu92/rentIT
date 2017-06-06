package com.rentIT.domain.repository;

import com.rentIT.domain.model.Property;
import com.rentIT.resource.model.SearchOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyAdvancedSearchRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Property> getAllPropertiesBySearchOptions(int first, int maxResults, String order, SearchOptions searchOptions) {
        String queryString = "select p from Property p";

        queryString += " where ";
        List<String> queryOptions = createQueryOptions(searchOptions);
        queryString += queryOptions.get(0);
        String subQuery = "";
        for (int i = 1; i < queryOptions.size(); i++) {
            subQuery += " and " + queryOptions.get(i);
        }

        queryString += subQuery;
        queryString += order.startsWith("+") ? " order by p.id ASC" : " order by p.id DESC";

        queryString = queryString.replace('[', '(');
        queryString = queryString.replace(']', ')');
        int page = first <= 0 ? 0 : first - 1;
        int perPage = maxResults * page;

        Query query = entityManager.createQuery(queryString);
        query.setFirstResult(page);
        query.setMaxResults(perPage);

        return entityManager.createQuery(queryString).getResultList();
    }

    private List<String> createQueryOptions(SearchOptions searchOptions) {
        List<String> queryOptions = new ArrayList<>();
        if (!StringUtils.isEmpty(searchOptions.getName())) {
            queryOptions.add("p.title='" + searchOptions.getName() + "'");
        }

        if (searchOptions.getAllowsPets() != null) {
            queryOptions.add("p.allowsPets='" + searchOptions.getAllowsPets() + "'");
        }

        if (!CollectionUtils.isEmpty(searchOptions.getCityId())) {
            queryOptions.add("p.city.id in " + searchOptions.getCityId());
        }

        if (searchOptions.getMaxPrice() != null && searchOptions.getMinPrice() != null) {
            queryOptions.add("p.price between " + searchOptions.getMinPrice() + " AND " + searchOptions.getMaxPrice());
        }

        if (!CollectionUtils.isEmpty(searchOptions.getNumberOfRooms())) {
            queryOptions.add("p.roomsNumber in " + searchOptions.getNumberOfRooms());
        }

        if (!CollectionUtils.isEmpty(searchOptions.getNumberOfStars())) {
            queryOptions.add("p.averageRating in " + searchOptions.getNumberOfStars());
        }

        return queryOptions;
    }

}
