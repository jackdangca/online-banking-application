package com.onlinebank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Repository
interface TransactionA2ARepository extends JpaRepository<TransactionA2A, Long> {

    List<TransactionA2A> findAllBySrcAccountIdOrDstAccountId(Long accountId1, Long accountId2);

    @Query("select u from A2ATransaction u where u.a2atransactionId = ?1 and ( u.srcAccountId = ?2 or u.dstAccountId = ?2 )")
    TransactionA2A findByA2atransactionIdAndAccountId(Long a2atransactionId, Long accountId);

}
