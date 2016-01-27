package com.onlinebank.user;

import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.user.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by p0wontnx on 1/25/16.
 */
@Transactional
public class UserServiceTests extends OnlineBankApplicationTests {

    @Autowired
    private UserService userService;


    @Before
    public void setUp() {
        super.setUp();
    }

    @Test(expected = BadRequestException.class)
    public void testBadUserRegistrationRequest() throws Exception {
        User user = new User();

        user = userService.register(user);

        Assert.assertNull("failure - expect returned user null", user);

    }

    @Test(expected = UserRegistrationFailedException.class)
    public void testFailedUserRegistrationRequest() throws Exception {
        User user1 = new User();
        // init user1
        user1.setFirst_name("Fouad");
        user1.setLast_name("Wahabi");
        user1.setMail("fouad.wahabi@gmail.com");
        user1.setPassword("password");
        user1.setCountry("TN");
        user1.setState("Tunis");
        user1.setZip(1002);
        user1.setAddress("Cite khadhra");
        user1.setCin("13449344");

        user1 = userService.register(user1);

        User user2 = new User();
        // init user2
        user2.setFirst_name("Fouad");
        user2.setLast_name("Wahabi");
        user2.setMail("fouad.wahabi@gmail.com");
        user2.setPassword("password");
        user2.setCountry("TN");
        user2.setState("Tunis");
        user2.setZip(1002);
        user2.setAddress("Cite khadhra");
        user2.setCin("13449344");

        user2 = userService.register(user2);

        Assert.assertNull("failure - expect returned user null", user1);
        Assert.assertNull("failure - expect returned user null", user2);

    }

    @Test
    public void testValidUserRegistration() throws Exception {
        User user = new User();
        // init user
        user.setFirst_name("Fouad");
        user.setLast_name("Wahabi");
        user.setMail("fouad.wahabi@gmail.com");
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin("0");

        user = userService.register(user);

        Assert.assertNotNull("failure - expect returned user not null", user);

    }

}
