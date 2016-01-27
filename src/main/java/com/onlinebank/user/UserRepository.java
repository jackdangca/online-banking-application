package com.onlinebank.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Repository
interface UserRepository extends JpaRepository<User, Long> {
}
