package com.onlinebank.promotion.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.promotion.*;
import com.onlinebank.promotion.exceptions.PromotionCreationFailed;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.promotion.utils.PromotionTypes;
import com.onlinebank.utils.ResponseBuilder;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by p0wontnx on 2/17/16.
 */
@RestController
@RequestMapping(path = "/api/promotion")
public class PromotionRESTController {

    private PromotionService promotionService;

    @Autowired
    public PromotionRESTController(PromotionService promotionService) {
        Assert.notNull(promotionService);
        this.promotionService = promotionService;
    }

    @RequestMapping(path = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listAllPromotions() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // retrieve promotion list
        List<Promotion> promotionList = promotionService.findAll();
        responseBuilder.setResponseResult(promotionList);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{promotionId}", method = RequestMethod.GET)
    public ResponseEntity<ObjectNode> listPromotionInfos(@PathVariable("promotionId") Long promotionId) throws PromotionNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        Promotion promotion = null;

        // retrieve promotion
        promotion = promotionService.find(promotionId);
        switch (promotion.getPromotionType()) {
            case PromotionTypes.BONUS_PROMOTION:
                promotion = promotionService.findBonusPromotion(promotionId);
                break;
            case PromotionTypes.TAX_PROMOTION:
                promotion = promotionService.findTaxPromotion(promotionId);
                break;
            case PromotionTypes.WITHDRAWAL_LIMIT_PROMOTION:
                promotion = promotionService.findWithdrawalLimitPromotion(promotionId);
                break;
        }
        responseBuilder.setResponseResult(promotion);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/bonus",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addBonusPromotion(PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // create bonus promotion
        promotionBonus = promotionService.add(promotionBonus);
        responseBuilder.setResponseResult(promotionBonus);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/tax",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addTaxPromotion(PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // create bonus promotion
        promotionTax = promotionService.add(promotionTax);
        responseBuilder.setResponseResult(promotionTax);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/add/withdrawal",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> addWithdrawalPromotion(PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // create promotionWithdrawalLimit promotion
        promotionWithdrawalLimit = promotionService.add(promotionWithdrawalLimit);
        responseBuilder.setResponseResult(promotionWithdrawalLimit);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{promotionId}/edit/bonus",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editBonusPromotion(@PathVariable("promotionId") Long promotionBonusId, PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // edit bonus promotion
        promotionBonus = promotionService.edit(promotionBonusId, promotionBonus);
        responseBuilder.setResponseResult(promotionBonus);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{promotionId}/edit/tax",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editTaxPromotion(@PathVariable("promotionId") Long promotionTaxId, PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // edit tax promotion
        promotionTax = promotionService.edit(promotionTaxId, promotionTax);
        responseBuilder.setResponseResult(promotionTax);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{promotionId}/edit/withdrawal",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectNode> editWithdrawalPromotion(@PathVariable("promotionId") Long promotionWithdrawalLimitId, PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        // edit withdrawalLimit promotion
        promotionWithdrawalLimit = promotionService.edit(promotionWithdrawalLimitId, promotionWithdrawalLimit);
        responseBuilder.setResponseResult(promotionWithdrawalLimit);

        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.OK.getReasonPhrase()).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{promotionId}/remove",
            method = RequestMethod.GET)
    public ResponseEntity removePromotion(@PathVariable("promotionId") Long promotionId) throws PromotionNotFoundException {

        // remove promotion
        promotionService.remove(promotionId);

        return new ResponseEntity(HttpStatus.OK);
    }

}
