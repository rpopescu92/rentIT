package com.rentIT.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Scanner;

@Service
@Slf4j
public class CityConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void init() throws IOException {
        Resource res = new ClassPathResource("/static/scripts/cities.sql");
        Scanner scanner = new Scanner(res.getInputStream());

        String query;
        try {
            while (!StringUtils.isEmpty(query = scanner.nextLine())) {
                save(query);
            }
        } catch(Exception e) {
            log.error(e.toString());
        }
        entityManager.close();
    }

    public void save(String query) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                entityManager.createNativeQuery(query).executeUpdate();
            }
        });
    }

}
