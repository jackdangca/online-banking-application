package com.onlinebank.account;

import com.onlinebank.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Repository
interface AccountRepository extends JpaRepository<User, Long> {
}
