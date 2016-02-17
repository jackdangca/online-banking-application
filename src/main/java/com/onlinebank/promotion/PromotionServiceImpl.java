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
    private PromotionBonusRepository promotionBonusRepository;
    private PromotionTaxRepository promotionTaxRepository;
    private PromotionWithdrawalLimitRepository promotionWithdrawalLimitRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionBonusRepository promotionBonusRepository, PromotionTaxRepository promotionTaxRepository, PromotionWithdrawalLimitRepository promotionWithdrawalLimitRepository) {
        Assert.notNull(promotionRepository);
        this.promotionRepository = promotionRepository;
        Assert.notNull(promotionBonusRepository);
        this.promotionBonusRepository = promotionBonusRepository;
        Assert.notNull(promotionTaxRepository);
        this.promotionTaxRepository = promotionTaxRepository;
        Assert.notNull(promotionWithdrawalLimitRepository);
        this.promotionWithdrawalLimitRepository = promotionWithdrawalLimitRepository;
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
    public PromotionTax findTaxPromotion(Long taxpromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        PromotionTax promotionTax = promotionTaxRepository.findOne(taxpromotionId);
        if (promotionTax == null) {
            throw new PromotionNotFoundException();
        }
        return promotionTax;
    }

    @Override
    public PromotionBonus findBonusPromotion(Long bonuspromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        PromotionBonus promotionBonus = promotionBonusRepository.findOne(bonuspromotionId);
        if (promotionBonus == null) {
            throw new PromotionNotFoundException();
        }
        return promotionBonus;
    }

    @Override
    public PromotionWithdrawalLimit findWithdrawalLimitPromotion(Long withdrawalLimitPromotionId) throws PromotionNotFoundException {
        // retrieve promotion infos
        PromotionWithdrawalLimit promotionWithdrawalLimit = promotionWithdrawalLimitRepository.findOne(withdrawalLimitPromotionId);
        if (promotionWithdrawalLimit == null) {
            throw new PromotionNotFoundException();
        }
        return promotionWithdrawalLimit;
    }
}
