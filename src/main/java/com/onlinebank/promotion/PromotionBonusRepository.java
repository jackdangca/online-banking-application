package com.onlinebank.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Repository
interface PromotionBonusRepository extends JpaRepository<PromotionBonus, Long> {
}
