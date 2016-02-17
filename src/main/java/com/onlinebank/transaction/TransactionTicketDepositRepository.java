package com.onlinebank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Repository
interface TransactionTicketDepositRepository extends JpaRepository<TransactionTicketDeposit, Long> {

    List<TransactionTicketDeposit> findAllByDstAccountId(Long accountId);

    @Query("select u from TicketDeposit u where u.ticketdepositId = ?1 and u.dstAccountId = ?2")
    TransactionTicketDeposit findByTicketdepositIdAndAccountId(Long transactionId, Long accountId);

}
