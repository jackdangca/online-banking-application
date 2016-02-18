package com.onlinebank.promotion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinebank.OnlineBankApplicationTests;
import com.onlinebank.promotion.exceptions.PromotionCreationFailed;
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

/**
 * Created by p0wontnx on 2/18/16.
 */
@Transactional
public class PromotionControllerTests extends OnlineBankApplicationTests {

    @Autowired
    private PromotionService promotionService;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testListAllpromotion() throws Exception, PromotionCreationFailed {

        // create promotion
        PromotionBonus promotionBonus = new PromotionBonus();

        promotionBonus.setPeriod(366);
        promotionBonus.setBonusPp(1.0);

        promotionBonus = promotionService.add(promotionBonus);

        String url = "/api/promotion";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        Assert.assertTrue("failure - expected not empty response", !result.getResponse().getContentAsString().isEmpty());

    }

    @Test
    public void testGetPromotionInfos() throws Exception, PromotionCreationFailed {

        // create promotion
        PromotionBonus promotionBonus = new PromotionBonus();

        promotionBonus.setPeriod(366);
        promotionBonus.setBonusPp(1.0);

        promotionBonus = promotionService.add(promotionBonus);

        String url = "/api/promotion/" + promotionBonus.getBonuspromotionId();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        String resultcontent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultcontent);

        Assert.assertEquals("failure - expected id equal to " + promotionBonus.getBonuspromotionId(), (long) promotionBonus.getBonuspromotionId(), (long) resultContentNode.get("result").get("bonuspromotionId").asLong());

    }

    @Test
    public void testPromotionNotFound() throws Exception {

        String url = "/api/promotion/" + 0;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 404", result.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void testValidBonusPromotionCeation() throws Exception {

        String url = "/api/promotion/add/bonus";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("bonusPp", Arrays.asList(new String[]{"1.0"}));
        accountInfos.put("period", Arrays.asList(new String[]{"365"}));

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
    public void testInValidBonusPromotionCeation() throws Exception {

        String url = "/api/promotion/add/bonus";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("bonusPp", Arrays.asList(new String[]{"1.0"}));
        //accountInfos.put("period", Arrays.asList(new String[]{"365"}));

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

        Assert.assertEquals("failure - response's error field don't have period error", "period", resultContentNode.get("errors").get(0).asText());

    }

    @Test
    public void testValidTaxPromotionCeation() throws Exception {

        String url = "/api/promotion/add/tax";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("taxPp", Arrays.asList(new String[]{"1.0"}));
        accountInfos.put("period", Arrays.asList(new String[]{"365"}));
        accountInfos.put("taxPt", Arrays.asList(new String[]{"1.0"}));

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
    public void testInValidTaxPromotionCeation() throws Exception {

        String url = "/api/promotion/add/tax";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("taxPp", Arrays.asList(new String[]{"1.0"}));
        accountInfos.put("period", Arrays.asList(new String[]{"365"}));
        //accountInfos.put("taxPt", Arrays.asList(new String[]{"1.0"}));

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

        Assert.assertEquals("failure - response's error field don't have taxPt error", "taxPt", resultContentNode.get("errors").get(0).asText());

    }

    @Test
    public void testValidWithdrawalLimitPromotionCeation() throws Exception {

        String url = "/api/promotion/add/withdrawal";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("limitPp", Arrays.asList(new String[]{"1.0"}));
        accountInfos.put("period", Arrays.asList(new String[]{"365"}));

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
    public void testInValidWithdrawalLimitPromotionCeation() throws Exception {

        String url = "/api/promotion/add/withdrawal";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        //accountInfos.put("limitPp", Arrays.asList(new String[]{"1.0"}));
        accountInfos.put("period", Arrays.asList(new String[]{"365"}));

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

        Assert.assertEquals("failure - response's error field don't have limitPp error", "limitPp", resultContentNode.get("errors").get(0).asText());

    }

    @Test
    public void testValidBonusPromotionEdit() throws Exception, PromotionCreationFailed {

        PromotionBonus promotionBonus = new PromotionBonus();

        // init bonus promotion
        promotionBonus.setBonusPp(1.0);
        promotionBonus.setPeriod(366);

        // add promotion
        promotionBonus = promotionService.add(promotionBonus);

        String url = "/api/promotion/" + promotionBonus.getBonuspromotionId() + "/edit/bonus";

        MultiValueMap<String, String> accountInfos = new LinkedMultiValueMap<String, String>();

        accountInfos.put("bonusPp", Arrays.asList(new String[]{"2.0"}));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.
                post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .params(accountInfos);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", HttpStatus.OK.value(), result.getResponse().getStatus());

        String resultContent = result.getResponse().getContentAsString();

        JsonNode resultContentNode = new ObjectMapper().readTree(resultContent);

        Assert.assertNotNull(resultContentNode.get("result"));

        Assert.assertEquals("failure - limitPp to be 2.0", 2.0, (double) resultContentNode.get("result").get("bonusPp").asDouble(), 0);

    }

    @Test
    public void testPromotionRemove() throws Exception, PromotionCreationFailed {

        PromotionBonus promotionBonus = new PromotionBonus();

        // init bonus promotion
        promotionBonus.setBonusPp(1.0);
        promotionBonus.setPeriod(366);

        // add promotion
        promotionBonus = promotionService.add(promotionBonus);

        String url = "/api/promotion/" + promotionBonus.getBonuspromotionId() + "/remove";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 200", result.getResponse().getStatus(), HttpStatus.OK.value());

        url = "/api/promotion/" + promotionBonus.getBonuspromotionId();

        result = mvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        Assert.assertEquals("failure - expected HTTP status 404", result.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

    }

}
