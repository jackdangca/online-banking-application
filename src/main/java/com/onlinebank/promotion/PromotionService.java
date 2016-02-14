package com.onlinebank.promotion;

import com.onlinebank.promotion.exceptions.PromotionNotFoundException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface PromotionService {

    List<Promotion> findAll();

    Promotion find(Long promotionId) throws PromotionNotFoundException;

    TaxPromotion findTaxPromotion(Long taxpromotionId) throws PromotionNotFoundException;

    BonusPromotion findBonusPromotion(Long bonuspromotionId) throws PromotionNotFoundException;

    WithdrawalLimitPromotion findWithdrawalLimitPromotion(Long withdrawalLimitPromotionId) throws PromotionNotFoundException;

}
