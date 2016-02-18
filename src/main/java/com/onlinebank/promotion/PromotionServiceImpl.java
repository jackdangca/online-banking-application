package com.onlinebank.promotion;

import com.onlinebank.promotion.exceptions.PromotionCreationFailed;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.utils.PromotionTypes;
import com.onlinebank.utils.Utils;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        switch (promotion.getPromotionType()) {
            case PromotionTypes.BONUS_PROMOTION:
                promotion = findBonusPromotion(promotionId);
                break;
            case PromotionTypes.TAX_PROMOTION:
                promotion = findTaxPromotion(promotionId);
                break;
            case PromotionTypes.WITHDRAWAL_LIMIT_PROMOTION:
                promotion = findWithdrawalLimitPromotion(promotionId);
                break;
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

    @Override
    public PromotionBonus add(PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed {

        // verify not null attributes
        if (Utils.isModelFieldNull(promotionBonus)) {
            throw new BadRequestException(promotionBonus);
        }
        // set promotion type
        promotionBonus.setPromotionType(PromotionTypes.BONUS_PROMOTION);

        try {
            // save bonus promotion
            promotionBonus = promotionBonusRepository.save(promotionBonus);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (promotionBonus.getPromotionId() != null) {
            promotionBonus.setBonuspromotionId(promotionBonus.getPromotionId());
            return promotionBonus;
        }
        throw new PromotionCreationFailed();

    }

    @Override
    public PromotionTax add(PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed {

        // verify not null attributes
        if (Utils.isModelFieldNull(promotionTax)) {
            throw new BadRequestException(promotionTax);
        }
        // set promotion type
        promotionTax.setPromotionType(PromotionTypes.TAX_PROMOTION);

        try {
            // save tax promotion
            promotionTax = promotionTaxRepository.save(promotionTax);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (promotionTax.getPromotionId() != null) {
            promotionTax.setTaxpromotionId(promotionTax.getPromotionId());
            return promotionTax;
        }
        throw new PromotionCreationFailed();

    }

    @Override
    public PromotionWithdrawalLimit add(PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed {

        // verify not null attributes
        if (Utils.isModelFieldNull(promotionWithdrawalLimit)) {
            throw new BadRequestException(promotionWithdrawalLimit);
        }
        // set promotion type
        promotionWithdrawalLimit.setPromotionType(PromotionTypes.WITHDRAWAL_LIMIT_PROMOTION);

        try {
            // save withdrawal promotion
            promotionWithdrawalLimit = promotionWithdrawalLimitRepository.save(promotionWithdrawalLimit);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (promotionWithdrawalLimit.getPromotionId() != null) {
            promotionWithdrawalLimit.setWithdrawallimitpromotionId(promotionWithdrawalLimit.getPromotionId());
            return promotionWithdrawalLimit;
        }
        throw new PromotionCreationFailed();

    }

    @Override
    public PromotionBonus edit(Long promotionBonusId, PromotionBonus promotionBonus) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        // retrieve bonus promotion
        PromotionBonus oldPromotionBonus = promotionBonusRepository.findOne(promotionBonusId);

        if (oldPromotionBonus == null) {
            throw new PromotionNotFoundException();
        }

        if (promotionBonus.getBonusPp() != null) {
            oldPromotionBonus.setBonusPp(promotionBonus.getBonusPp());
        }

        if (promotionBonus.getPeriod() != null) {
            oldPromotionBonus.setPeriod(promotionBonus.getPeriod());
        }

        try {
            // save tax promotion
            oldPromotionBonus = promotionBonusRepository.save(oldPromotionBonus);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (oldPromotionBonus.getBonuspromotionId() != null) {
            return oldPromotionBonus;
        }
        throw new PromotionCreationFailed();

    }

    @Override
    public PromotionTax edit(Long promotionTaxId, PromotionTax promotionTax) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        // retrieve tax promotion
        PromotionTax oldPromotionTax = promotionTaxRepository.findOne(promotionTaxId);

        if (oldPromotionTax == null) {
            throw new PromotionNotFoundException();
        }

        if (oldPromotionTax.getTaxPp() != null) {
            oldPromotionTax.setTaxPp(promotionTax.getTaxPp());
        }

        if (oldPromotionTax.getPeriod() != null) {
            oldPromotionTax.setPeriod(oldPromotionTax.getPeriod());
        }

        if (oldPromotionTax.getTaxPt() != null) {
            oldPromotionTax.setTaxPt(promotionTax.getTaxPt());
        }

        try {
            // save tax promotion
            oldPromotionTax = promotionTaxRepository.save(oldPromotionTax);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (oldPromotionTax.getTaxpromotionId() != null) {
            return oldPromotionTax;
        }
        throw new PromotionCreationFailed();

    }

    @Override
    public PromotionWithdrawalLimit edit(Long promotionWithdrawalLimitId, PromotionWithdrawalLimit promotionWithdrawalLimit) throws BadRequestException, PromotionCreationFailed, PromotionNotFoundException {

        // retrieve withdrawallimit promotion
        PromotionWithdrawalLimit oldPromotionWithdrawalLimit = promotionWithdrawalLimitRepository.findOne(promotionWithdrawalLimitId);

        if (oldPromotionWithdrawalLimit == null) {
            throw new PromotionNotFoundException();
        }

        if (oldPromotionWithdrawalLimit.getLimitPp() != null) {
            oldPromotionWithdrawalLimit.setLimitPp(oldPromotionWithdrawalLimit.getLimitPp());
        }

        if (oldPromotionWithdrawalLimit.getPeriod() != null) {
            oldPromotionWithdrawalLimit.setPeriod(oldPromotionWithdrawalLimit.getPeriod());
        }

        try {
            // save withdrawal limit promotion
            oldPromotionWithdrawalLimit = promotionWithdrawalLimitRepository.save(oldPromotionWithdrawalLimit);
        } catch (DataIntegrityViolationException e) {
            throw new PromotionCreationFailed();
        }
        // verify if promotion was added successfully
        if (oldPromotionWithdrawalLimit.getWithdrawallimitpromotionId() != null) {
            return oldPromotionWithdrawalLimit;
        }
        throw new PromotionCreationFailed();

    }

    public void remove(long promotionId) throws PromotionNotFoundException {

        // retrieve promotion
        Promotion promotion = promotionRepository.findOne(promotionId);
        if (promotion == null) {
            throw new PromotionNotFoundException();
        }
        promotionRepository.delete(promotion);
    }

}
