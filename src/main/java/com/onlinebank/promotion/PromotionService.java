package com.onlinebank.promotion;

import com.onlinebank.promotion.exceptions.PromotionNotFoundException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface PromotionService {

    List<Promotion> findAll();

    Promotion find(Long promotionId) throws PromotionNotFoundException;

    PromotionTax findTaxPromotion(Long taxpromotionId) throws PromotionNotFoundException;

    PromotionBonus findBonusPromotion(Long bonuspromotionId) throws PromotionNotFoundException;

    PromotionWithdrawalLimit findWithdrawalLimitPromotion(Long withdrawalLimitPromotionId) throws PromotionNotFoundException;

}
