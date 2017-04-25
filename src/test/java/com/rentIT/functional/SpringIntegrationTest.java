package com.rentIT.functional;

import com.rentIT.RentItApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(
        loader = SpringBootContextLoader.class,
        classes = RentItApplication.class
)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public abstract class SpringIntegrationTest {
}
