package com.onlinebank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Repository
interface AccountTermRepository extends JpaRepository<AccountTerm, Long> {
}
