package com.onlinebank.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by p0wontnx on 1/21/16.
 */
@Configuration
class UserConfig {

    @Bean
    public UserRepository userRepository() {
        return null;
    }

}
