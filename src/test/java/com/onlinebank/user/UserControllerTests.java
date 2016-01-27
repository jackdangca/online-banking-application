package com.onlinebank.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebank.OnlineBankApplicationTests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * Created by p0wontnx on 1/27/16.
 */
@Transactional
public class UserControllerTests extends OnlineBankApplicationTests {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testValidUserRegistration() throws Exception {

        String url = "/api/user/register";

        MultiValueMap<String, String> userInfos = new LinkedMultiValueMap<String, String>();

        userInfos.put("first_name", Arrays.asList(new String[]{"Fouad"}));
        userInfos.put("last_name", Arrays.asList(new String[]{"Wahabi"}));
        userInfos.put("mail", Arrays.asList(new String[]{"fouad.wahabi@gmail.com"}));
        userInfos.put("password", Arrays.asList(new String[]{"password"}));
        userInfos.put("country", Arrays.asList(new String[]{"TN"}));
        userInfos.put("state", Arrays.asList(new String[]{"Tunis"}));
        userInfos.put("zip", Arrays.asList(new String[]{"1002"}));
        userInfos.put("address", Arrays.asList(new String[]{"Cite khadhra"}));
        userInfos.put("cin", Arrays.asList(new String[]{"13449344"}));

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
        userInfos.put("mail", Arrays.asList(new String[]{"fouad.wahabi@gmail.com"}));
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

}
