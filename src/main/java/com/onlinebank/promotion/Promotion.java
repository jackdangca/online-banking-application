package com.onlinebank.promotion;

import javax.persistence.*;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Promotion {

    @Id
    @GeneratedValue
    private Long promotionId;

    private String bannier;

    private String promotionType;

    public Long getPromotionId() {
        return promotionId;
    }

    public Promotion setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public String getBannier() {
        return bannier;
    }

    public Promotion setBannier(String bannier) {
        this.bannier = bannier;
        return this;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public Promotion setPromotionType(String promotionType) {
        this.promotionType = promotionType;
        return this;
    }
}
