package com.onlinebank.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.user.User;
import com.onlinebank.user.UserService;
import com.onlinebank.utils.Utils;
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
 * Created by p0wontnx on 1/31/16.
 */
@Transactional
public class AccountControllerTests extends OnlineBankApplicationTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        super.setUp();
    }


    //region Account infos tests
    @Test
    public void testListAllUserAccounts() throws Exception {

        // register user to add account
        User user = new User();
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

        Account account = new Account();
        // init account
        account.setLabel("Account1");

        accountService.add(account, user);

        String url = "/api/user/" + user.getUserId() + "/account";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        Assert.assertTrue("failure - expected not empty response", !result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testListUserAccountInfos() throws Exception {

        // register user to add account
        User user = new User();
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

        Account account = new Account();
        // init account
        account.setLabel("Account1");

        account = accountService.add(account, user);

        String url = "/api/user/" + user.getUserId() + "/account/" + account.getAccountId();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        String resultcontent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultcontent);

        Assert.assertEquals("failure - expected id equal to " + account.getAccountId(), (long) account.getAccountId(), resultContentNode.get("result").get("accountId").asLong());

    }

    @Test
    public void testUserAccountNotFound() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/0";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 404", HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

    }
    //endregion

    //region Account creation tests
    @Test
    public void testValidAccountTermCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/term";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("duration", Arrays.asList(new String[]{"365"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

    }

    @Test
    public void testInValidAccountTermCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/term";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 400", HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("errors"));

        Assert.assertEquals("failure - response don't have exactly one error field", 2, resultContentNode.get("errors").size());

        Assert.assertEquals("failure - response's error field don't have duration error", "duration", resultContentNode.get("errors").get(0).asText());

        Assert.assertEquals("failure - response's error field don't have bonuspromotionId error", "bonuspromotionId", resultContentNode.get("errors").get(1).asText());

    }

    @Test
    public void testValidAccountCurrentCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/current";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

    }

    @Test
    public void testInValidAccountCurrentCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/current";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 400", HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("errors"));

        Assert.assertEquals("failure - response don't have exactly one error field", 1, resultContentNode.get("errors").size());

        Assert.assertEquals("failure - response's error field don't have taxpromotionId error", "taxpromotionId", resultContentNode.get("errors").get(0).asText());

    }

    @Test
    public void testValidAccountSavingCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/saving";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));
        accountInfos.put("withdrawallimitpromotionId", Arrays.asList(new String[]{"1"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

    }

    @Test
    public void testInValidAccountSavingCreation() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/saving";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 400", HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("errors"));

        Assert.assertEquals("failure - response don't have exactly one error field", 3, resultContentNode.get("errors").size());

        Assert.assertEquals("failure - response's error field don't have bonuspromotionId error", "bonuspromotionId", resultContentNode.get("errors").get(0).asText());

        Assert.assertEquals("failure - response's error field don't have withdrawallimitpromotionId error", "withdrawallimitpromotionId", resultContentNode.get("errors").get(1).asText());

        Assert.assertEquals("failure - response's error field don't have taxpromotionId error", "taxpromotionId", resultContentNode.get("errors").get(2).asText());

    }
    //endregion

    //region Account editing tests
    @Test
    public void testValidAccountTermEditing() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/term";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("duration", Arrays.asList(new String[]{"365"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        url = "/api/user/" + user.getUserId() + "/account/" + resultContentNode.get("result").get("accountId").asText() + "/edit/term";

        accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTestEdit"}));
        accountInfos.put("duration", Arrays.asList(new String[]{"365"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));

        requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        resultContent = result.getResponse().getContentAsString();

        resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        Assert.assertEquals("failure - expected label AccountTestEdit", "AccountTestEdit", resultContentNode.get("result").get("label").asText());

    }

    @Test
    public void testValidAccountCurrentEditing() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/current";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        url = "/api/user/" + user.getUserId() + "/account/" + resultContentNode.get("result").get("accountId").asText() + "/edit/current";

        accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTestEdit"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        resultContent = result.getResponse().getContentAsString();

        resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        Assert.assertEquals("failure - expected label AccountTestEdit", "AccountTestEdit", resultContentNode.get("result").get("label").asText());

    }

    @Test
    public void testValidAccountSavingEditing() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/saving";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));
        accountInfos.put("withdrawallimitpromotionId", Arrays.asList(new String[]{"1"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        url = "/api/user/" + user.getUserId() + "/account/" + resultContentNode.get("result").get("accountId").asText() + "/edit/saving";

        accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTestEdit"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));
        accountInfos.put("withdrawallimitpromotionId", Arrays.asList(new String[]{"1"}));
        accountInfos.put("taxpromotionId", Arrays.asList(new String[]{"2"}));

        requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        resultContent = result.getResponse().getContentAsString();

        resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        Assert.assertEquals("failure - expected label AccountTestEdit", "AccountTestEdit", resultContentNode.get("result").get("label").asText());


    }
    //endregion

    //region Account delete tests
    @Test
    public void testValidAccountTermRemove() throws Exception {

        // register user to add account
        User user = new User();
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

        String url = "/api/user/" + user.getUserId() + "/account/add/term";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("label", Arrays.asList(new String[]{"AccountTest"}));
        accountInfos.put("duration", Arrays.asList(new String[]{"365"}));
        accountInfos.put("bonuspromotionId", Arrays.asList(new String[]{"4"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        url = "/api/user/" + user.getUserId() + "/account/" + resultContentNode.get("result").get("accountId") + "/remove";

        requestBuilder = MockMvcRequestBuilders.
                get(url);

        result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

    }
    //endregion

}
