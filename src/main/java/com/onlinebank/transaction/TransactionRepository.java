package com.onlinebank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by p0wontnx on 2/17/16.
 */
@Repository
interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByTransactionId(Long transactionId);

}
