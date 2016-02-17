package com.onlinebank.promotion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 * Created by p0wontnx on 2/14/16.
 */
@Entity(name = "TaxPromotion")
@PrimaryKeyJoinColumn(name = "taxpromotionId", referencedColumnName = "promotionId")
public class PromotionTax extends Promotion {

    @Column(insertable = false, updatable = false)
    private Long taxpromotionId;

    @NotNull
    private Double taxPp;

    @NotNull
    private Double taxPt;

    @NotNull
    private Integer period;

    public Double getTaxPp() {
        return taxPp;
    }

    public PromotionTax setTaxPp(Double taxPp) {
        this.taxPp = taxPp;
        return this;
    }

    public Double getTaxPt() {
        return taxPt;
    }

    public PromotionTax setTaxPt(Double taxPt) {
        this.taxPt = taxPt;
        return this;
    }

    public Long getTaxpromotionId() {
        return taxpromotionId;
    }

    public PromotionTax setTaxpromotionId(Long taxpromotionId) {
        this.taxpromotionId = taxpromotionId;
        return this;
    }

    public Integer getPeriod() {
        return period;
    }

    public PromotionTax setPeriod(Integer period) {
        this.period = period;
        return this;
    }

}
