package com.onlinebank.promotion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 2/14/16.
 */
@Entity(name = "BonusPromotion")
@PrimaryKeyJoinColumn(name = "bonuspromotionId", referencedColumnName = "promotionId")
public class BonusPromotion extends Promotion {

    @Column(insertable = false, updatable = false)
    private Long bonuspromotionId;

    @NotNull
    private Double bonusPp;

    @NotNull
    private Integer period;

    public Double getBonusPp() {
        return bonusPp;
    }

    public BonusPromotion setBonusPp(Double bonusPp) {
        this.bonusPp = bonusPp;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public BonusPromotion setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public Long getBonuspromotionId() {
        return bonuspromotionId;
    }

    public BonusPromotion setBonuspromotionId(Long bonuspromotionId) {
        this.bonuspromotionId = bonuspromotionId;
        return this;
    }
}
