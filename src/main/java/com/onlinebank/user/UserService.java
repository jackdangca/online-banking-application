package com.onlinebank.user;

import com.onlinebank.user.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface UserService {
    User register(User user) throws BadRequestException,
            UserRegistrationFailedException;
}
