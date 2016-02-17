package com.onlinebank.promotion.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.promotion.Promotion;
import com.onlinebank.promotion.PromotionService;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.utils.PromotionTypes;
import com.onlinebank.utils.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class PromotionController {

    private PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
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

}
