package com.onlinebank.promotion;

import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Service
class PromotionServiceImpl implements PromotionService {

    private PromotionRepository promotionRepository;
    private BonusPromotionRepository bonusPromotionRepository;
    private TaxPromotionRepository taxPromotionRepository;
    private WithdrawalLimitPromotionRepository withdrawalLimitPromotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, BonusPromotionRepository bonusPromotionRepository, TaxPromotionRepository taxPromotionRepository, WithdrawalLimitPromotionRepository withdrawalLimitPromotionRepository) {
        Assert.notNull(promotionRepository);
        this.promotionRepository = promotionRepository;
        Assert.notNull(bonusPromotionRepository);
        this.bonusPromotionRepository = bonusPromotionRepository;
        Assert.notNull(taxPromotionRepository);
        this.taxPromotionRepository = taxPromotionRepository;
        Assert.notNull(withdrawalLimitPromotionRepository);
        this.withdrawalLimitPromotionRepository = withdrawalLimitPromotionRepository;
    }

    @Override
    public List<Promotion> findAll() {
        // retrieve all prmotions
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions;
    }

    @Override
    public Promotion find(Long promotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        Promotion promotion = promotionRepository.findOne(promotionId);
        if (promotion == null) {
            throw new PromotionNotFoundException();
        }
        return promotion;
    }

    @Override
    public TaxPromotion findTaxPromotion(Long taxpromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        TaxPromotion taxPromotion = taxPromotionRepository.findOne(taxpromotionId);
        if (taxPromotion == null) {
            throw new PromotionNotFoundException();
        }
        return taxPromotion;
    }

    @Override
    public BonusPromotion findBonusPromotion(Long bonuspromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        BonusPromotion bonusPromotion = bonusPromotionRepository.findOne(bonuspromotionId);
        if (bonusPromotion == null) {
            throw new PromotionNotFoundException();
        }
        return bonusPromotion;
    }

    @Override
    public WithdrawalLimitPromotion findWithdrawalLimitPromotion(Long withdrawalLimitPromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        WithdrawalLimitPromotion withdrawalLimitPromotion = withdrawalLimitPromotionRepository.findOne(withdrawalLimitPromotionId);
        if (withdrawalLimitPromotion == null) {
            throw new PromotionNotFoundException();
        }
        return withdrawalLimitPromotion;
    }
}
