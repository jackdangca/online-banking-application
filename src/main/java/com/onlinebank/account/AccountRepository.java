package com.onlinebank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Repository
interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    Account findOneByAccountIdAndUserId(Long accountId, Long userId);

    Account findOneByNumber(Long number);
}
