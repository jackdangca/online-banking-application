package com.onlinebank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Repository
interface TransactionCheckRepository extends JpaRepository<TransactionCheck, Long> {

    List<TransactionCheck> findAllBySrcAccountIdOrDstAccountId(Long accountId1, Long accountId2);

    @Query("select u from CheckTransaction u where u.checktransactionId = ?1 and ( u.srcAccountId = ?2 or u.dstAccountId = ?2 )")
    TransactionCheck findByChecktransactionIdAndAccountId(Long transactionId, Long accountId);
}
