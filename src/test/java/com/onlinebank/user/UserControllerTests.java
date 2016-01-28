package com.onlinebank.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by p0wontnx on 1/27/16.
 */
@Transactional
public class UserControllerTests extends OnlineBankApplicationTests {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testListAllUsers() throws Exception {

        String url = "/api/user/";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        Assert.assertTrue("failure - expected not empy response", !result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testListUserInfos() throws Exception {

        User user = new User();
        // init user
        user.setFirst_name("Fouad");
        user.setLast_name("Wahabi");
        user.setMail(Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8));
        user.setPassword("password");
        user.setCountry("TN");
        user.setState("Tunis");
        user.setZip(1002);
        user.setAddress("Cite khadhra");
        user.setCin(Utils.generateString(new Random(), "0123456789", 8));

        user = userService.register(user);

        String url = "/api/user/" + user.getUser_id();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertEquals("failure - expected id equal to " + user.getUser_id(), (long) user.getUser_id(), resultContentNode.get("user_id").asLong());

    }

    @Test
    public void testUserNotFound() throws Exception {

        String url = "/api/user/0";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 404", result.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void testValidUserRegistration() throws Exception {

        String url = "/api/user/register";

        MultiValueMap<String, String> userInfos = new LinkedMultiValueMap<String, String>();

        userInfos.put("first_name", Arrays.asList(new String[]{"Fouad"}));
        userInfos.put("last_name", Arrays.asList(new String[]{"Wahabi"}));
        userInfos.put("mail", Arrays.asList(new String[]{Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8)}));
        userInfos.put("password", Arrays.asList(new String[]{"password"}));
        userInfos.put("country", Arrays.asList(new String[]{"TN"}));
        userInfos.put("state", Arrays.asList(new String[]{"Tunis"}));
        userInfos.put("zip", Arrays.asList(new String[]{"1002"}));
        userInfos.put("address", Arrays.asList(new String[]{"Cite khadhra"}));
        userInfos.put("cin", Arrays.asList(new String[]{Utils.generateString(new Random(), "0123456789", 8)}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(userInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

    }

    @Test
    public void testBadUserRegistrationRequest() throws Exception {

        String url = "/api/user/register";

        MultiValueMap<String, String> userInfos = new LinkedMultiValueMap<String, String>();

        userInfos.put("first_name", Arrays.asList(new String[]{"Fouad"}));
        userInfos.put("last_name", Arrays.asList(new String[]{"Wahabi"}));
        userInfos.put("mail", Arrays.asList(new String[]{Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8)}));
        userInfos.put("password", Arrays.asList(new String[]{"password"}));
        userInfos.put("country", Arrays.asList(new String[]{"TN"}));
        userInfos.put("state", Arrays.asList(new String[]{"Tunis"}));
        userInfos.put("zip", Arrays.asList(new String[]{"1002"}));
        userInfos.put("address", Arrays.asList(new String[]{"Cite khadhra"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(userInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 400", result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("errors"));

        Assert.assertEquals("failure - response don't have exactly one error field", resultContentNode.get("errors").size(), 1);

        Assert.assertEquals("failure - response's error field don't have cin error", resultContentNode.get("errors").get(0).asText(), "cin");

    }

    @Test
    public void testValidUserEditing() throws Exception {

        String url = "/api/user/register";

        MultiValueMap<String, String> userInfos = new LinkedMultiValueMap<String, String>();

        userInfos.put("first_name", Arrays.asList(new String[]{"Fouad"}));
        userInfos.put("last_name", Arrays.asList(new String[]{"Wahabi"}));
        userInfos.put("mail", Arrays.asList(new String[]{Utils.generateString(new Random(), "abcdefghijklmnopqrstuvwxyz", 8)}));
        userInfos.put("password", Arrays.asList(new String[]{"password"}));
        userInfos.put("country", Arrays.asList(new String[]{"TN"}));
        userInfos.put("state", Arrays.asList(new String[]{"Tunis"}));
        userInfos.put("zip", Arrays.asList(new String[]{"1002"}));
        userInfos.put("address", Arrays.asList(new String[]{"Cite khadhra"}));
        userInfos.put("cin", Arrays.asList(new String[]{Utils.generateString(new Random(), "0123456789", 8)}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(userInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        url = "/api/user/" + resultContentNode.get("user").get("user_id").asLong() + "/edit";

        userInfos = new LinkedMultiValueMap<String, String>();

        userInfos.put("first_name", Arrays.asList(new String[]{"FouadW"}));

        requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(userInfos);

        result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        resultContent = result.getResponse().getContentAsString();

        resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertEquals("", "FouadW", resultContentNode.get("user").get("first_name").asText());

    }

}
