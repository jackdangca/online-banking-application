package com.onlinebank.user;

import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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

    @Test
    public void testListAllUsers() {

        List<User> users = userService.findAll();

        Assert.assertNotNull("failure - expect returned list of users not null", users);

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
        user1.setFirstName("Fouad");
        user1.setLastName("Wahabi");
        String mail1 = Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8);
        user1.setMail(mail1);
        user1.setPassword("password");
        user1.setCountry("TN");
        user1.setState("Tunis");
        user1.setZip(1002);
        user1.setAddress("Cite khadhra");
        String cin1 = Utils.generateString(new Random(), "0123456789", 8);
        user1.setCin(cin1);

        user1 = userService.register(user1);

        Assert.assertNotNull("failure - expect returned user1 not null", user1);

        User user2 = new User();
        // init user2
        user2.setFirstName("Fouad");
        user2.setLastName("Wahabi");
        String mail2 = Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8);
        user2.setMail(mail2);
        user2.setPassword("password");
        user2.setCountry("TN");
        user2.setState("Tunis");
        user2.setZip(1002);
        user2.setAddress("Cite khadhra");
        user2.setCin(cin1);

        user2 = userService.register(user2);

    }

    @Test
    public void testValidUserRegistration() throws Exception {
        User user = new User();
        // init user
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        Assert.assertNotNull("failure - expect returned user not null", user);

    }

    @Test
    public void testValidUserEditing() throws Exception {
        User user = new User();
        // init user
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        Assert.assertNotNull("failure - expect returned user not null", user);

        user.setFirstName("FouadW");

        user = userService.edit(user.getUserId(), user);

        Assert.assertEquals("failure - expect first_name after edit FouadW", "FouadW", user.getFirstName());
    }


    @Test(expected = UserEditingFailedException.class)
    public void testUserFailingEdit() throws Exception {
        User user1 = new User();
        // init user1
        user1.setFirstName("Fouad");
        user1.setLastName("Wahabi");
        String mail1 = Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8);
        user1.setMail(mail1);
        user1.setPassword("password");
        user1.setCountry("TN");
        user1.setState("Tunis");
        user1.setZip(1002);
        user1.setAddress("Cite khadhra");
        String cin1 = Utils.generateString(new Random(), "0123456789", 8);
        user1.setCin(cin1);

        user1 = userService.register(user1);

        Assert.assertNotNull("failure - expect returned user1 not null", user1);

        User user2 = new User();
        // init user2
        user2.setFirstName("Fouad");
        user2.setLastName("Wahabi");
        String mail2 = Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8);
        user2.setMail(mail2);
        user2.setPassword("password");
        user2.setCountry("TN");
        user2.setState("Tunis");
        user2.setZip(1002);
        user2.setAddress("Cite khadhra");
        String cin2 = Utils.generateString(new Random(), "0123456789", 8);
        user2.setCin(cin2);

        user2 = userService.register(user2);

        Assert.assertNotNull("failure - expect returned user2 not null", user2);

        User user = new User();
        // edit with existing cin
        user.setCin(cin1);
        user.setMail(mail1);

        user = userService.edit(user2.getUserId(), user);

    }

    @Test(expected = UserNotFoundException.class)
    public void testUserRemove() throws Exception {

        User user = new User();
        // init user
        user.setFirstName("Fouad");
        user.setLastName("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        // register user
        user = userService.register(user);

        // remove user
        userService.remove(user.getUserId());

        user = userService.find(user.getUserId());

    }

}
