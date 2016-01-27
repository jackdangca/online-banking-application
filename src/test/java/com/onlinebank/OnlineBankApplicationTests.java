package com.onlinebank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnlineBankApplication.class)
@WebAppConfiguration
public class OnlineBankApplicationTests {


    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    /**
     *
     */
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     *
     * @param controller A controller object to be tested.
     */
    protected void setUp(Object controller) {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads() {
    }

}
