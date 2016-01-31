package com.onlinebank.user;

import com.onlinebank.utils.exceptions.BadRequestException;
import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;

import java.util.List;

/**
 * Created by p0wontnx on 1/21/16.
 */
public interface UserService {

    List<User> findAll();

    User find(long id) throws UserNotFoundException;

    User register(User user) throws BadRequestException,
            UserRegistrationFailedException;

    User edit(long id, User user) throws UserNotFoundException, UserEditingFailedException;

    void remove(long id) throws UserNotFoundException;

}
